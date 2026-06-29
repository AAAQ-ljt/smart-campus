package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.po.ResourceInfo;
import com.smart.campus.entity.query.ResourceInfoQuery;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.redis.RedisComponent;
import com.smart.campus.service.FileUploadService;
import com.smart.campus.service.ResourceInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resourceInfo")
public class ResourceInfoController extends ABaseController {

    private static final Logger log = LoggerFactory.getLogger(ResourceInfoController.class);

    @Resource
    private ResourceInfoService resourceInfoService;

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private RedisComponent redisComponent;

    @Value("${project.folder:./}")
    private String projectFolder;

    /**
     * 加载完整目录树（仅目录节点）
     */
    @PostMapping("/loadResourceTree")
    public ResponseVO<List<ResourceInfo>> loadResourceTree() {
        ResourceInfoQuery query = new ResourceInfoQuery();
        query.setNodeType(1);
        query.setOrderBy("sort_order asc, create_time desc");
        List<ResourceInfo> list = resourceInfoService.findListByParam(query);
        return getSuccessResponseVO(list);
    }

    /**
     * 加载指定目录下的子节点（目录+文件）
     */
    @PostMapping("/loadResourceList")
    public ResponseVO<List<ResourceInfo>> loadResourceList(@RequestParam(defaultValue = "0") String parentId) {
        ResourceInfoQuery query = new ResourceInfoQuery();
        query.setParentId(parentId);
        query.setOrderBy("node_type asc, sort_order asc, create_time desc");
        List<ResourceInfo> list = resourceInfoService.findListByParam(query);
        return getSuccessResponseVO(list);
    }

    /**
     * 新增目录
     */
    @PostMapping("/addResource")
    public ResponseVO<String> addResource(@RequestBody ResourceInfo bean) {
        if (bean.getNodeType() == null) {
            bean.setNodeType(1);
        }
        if (bean.getParentId() == null) {
            bean.setParentId("0");
        }

        // 计算sortOrder
        ResourceInfoQuery query = new ResourceInfoQuery();
        query.setParentId(bean.getParentId());
        query.setNodeType(bean.getNodeType());
        query.setOrderBy("sort_order desc");
        List<ResourceInfo> siblings = resourceInfoService.findListByParam(query);
        if (siblings == null || siblings.isEmpty()) {
            bean.setSortOrder(1);
        } else {
            bean.setSortOrder((siblings.get(0).getSortOrder() == null ? 0 : siblings.get(0).getSortOrder()) + 1);
        }

        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        resourceInfoService.add(bean);
        return getSuccessResponseVO(bean.getResourceId());
    }

    /**
     * 更新资源（重命名等）
     */
    @PostMapping("/updateResource")
    public ResponseVO<Integer> updateResource(@RequestBody ResourceInfo bean) {
        ResourceInfo updateBean = new ResourceInfo();
        updateBean.setResourceName(bean.getResourceName());
        updateBean.setUpdateTime(new Date());
        Integer result = resourceInfoService.updateResourceInfoByResourceId(updateBean, bean.getResourceId());
        return getSuccessResponseVO(result);
    }

    /**
     * 删除资源（目录需检查是否为空）
     */
    @PostMapping("/deleteResource")
    public ResponseVO<Integer> deleteResource(@RequestParam String resourceId) {
        ResourceInfo current = resourceInfoService.getResourceInfoByResourceId(resourceId);
        if (current == null) {
            throw new BusinessException("资源不存在");
        }

        // 如果是目录，检查是否有子节点
        if (current.getNodeType() != null && current.getNodeType() == 1) {
            ResourceInfoQuery childQuery = new ResourceInfoQuery();
            childQuery.setParentId(resourceId);
            Integer childCount = resourceInfoService.findCountByParam(childQuery);
            if (childCount != null && childCount > 0) {
                throw new BusinessException("该目录下存在子文件夹或文件，无法删除");
            }
        }

        Integer result = resourceInfoService.deleteResourceInfoByResourceId(resourceId);

        // 清理磁盘文件和 Redis 会话
        if (current.getNodeType() != null && current.getNodeType() == 2) {
            fileUploadService.cleanupResource(resourceId, current.getResourcePath());
        }

        return getSuccessResponseVO(result);
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{resourceId}")
    public void download(@PathVariable String resourceId,
                         @RequestParam String token,
                         @RequestParam(required = false, defaultValue = "false") boolean view,
                         HttpServletResponse response) {
        // 手动校验 token（下载走浏览器默认行为，无法携带自定义 header）
        if (token == null || token.isEmpty() || redisComponent.getAdminLoginUserId(token) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        ResourceInfo info = resourceInfoService.getResourceInfoByResourceId(resourceId);
        if (info == null || info.getNodeType() == null || info.getNodeType() != 2) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path filePath = resolvePath(info.getResourcePath());
        if (!Files.exists(filePath)) {
            log.warn("下载文件不存在: resourceId={}, path={}", resourceId, filePath);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fileName = info.getOriginalName() != null ? info.getOriginalName() : info.getResourceName();
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

        if (view) {
            // 预览模式：根据扩展名设置 MIME，不设 Content-Disposition，浏览器内联显示
            String mimeType = getMimeType(fileName);
            response.setContentType(mimeType);
        } else {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
        }
        response.setContentLengthLong(filePath.toFile().length());

        try (OutputStream out = response.getOutputStream();
             InputStream in = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取视频封面图
     */
    @GetMapping("/cover/{resourceId}")
    public void cover(@PathVariable String resourceId,
                      @RequestParam String token,
                      HttpServletResponse response) {
        if (token == null || token.isEmpty() || redisComponent.getAdminLoginUserId(token) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        ResourceInfo info = resourceInfoService.getResourceInfoByResourceId(resourceId);
        if (info == null || info.getCoverUrl() == null) {
            log.warn("封面获取失败: resourceId={}, coverUrl 为空", resourceId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path coverPath = resolvePath(info.getCoverUrl());
        if (!Files.exists(coverPath)) {
            log.warn("封面文件不存在: resourceId={}, path={}", resourceId, coverPath);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("image/jpeg");
        response.setContentLengthLong(coverPath.toFile().length());

        try (OutputStream out = response.getOutputStream();
             InputStream in = Files.newInputStream(coverPath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 视频文件服务（m3u8 索引 + ts 分片），统一入口避免 Spring PathPattern 对 .m3u8 的匹配问题
     */
    @GetMapping("/video/{resourceId}/{filename:.+}")
    public void videoFile(@PathVariable String resourceId,
                          @PathVariable String filename,
                          @RequestParam String token,
                          HttpServletResponse response) throws IOException {
        if (token == null || token.isEmpty() || redisComponent.getAdminLoginUserId(token) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        ResourceInfo info = resourceInfoService.getResourceInfoByResourceId(resourceId);
        if (info == null || info.getResourcePath() == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path hlsDir = resolvePath(info.getResourcePath()).getParent().resolve("hls");
        log.debug("视频请求: resourceId={}, filename={}, hlsDir={}", resourceId, filename, hlsDir);

        if ("index.m3u8".equals(filename)) {
            Path m3u8File = hlsDir.resolve(resourceId + ".m3u8");
            if (!Files.exists(m3u8File)) {
                log.warn("m3u8 文件不存在: resourceId={}, path={}", resourceId, m3u8File);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String content = Files.readString(m3u8File);
            // 将 ts 引用从 {resourceId}_00001.ts 改写为 segment_00001.ts，并附带 token 参数
            content = content.replace(resourceId + "_", "segment_");
            content = content.replaceAll("(segment_\\d+\\.ts)", "$1?token=" + token);
            response.setContentType("application/vnd.apple.mpegurl");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(content);
            return;
        }

        if (filename.startsWith("segment_") && filename.endsWith(".ts")) {
            // segment_00001.ts → {resourceId}_00001.ts
            String tsFileName = filename.replace("segment_", resourceId + "_");
            Path tsFile = hlsDir.resolve(tsFileName);
            if (!Files.exists(tsFile)) {
                log.warn("ts 文件不存在: resourceId={}, path={}", resourceId, tsFile);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            response.setContentType("video/mp2t");
            response.setContentLengthLong(tsFile.toFile().length());
            try (OutputStream out = response.getOutputStream();
                 InputStream in = Files.newInputStream(tsFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            }
            return;
        }

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * 移动资源到目标目录
     */
    @PostMapping("/moveResource")
    public ResponseVO<Integer> moveResource(@RequestParam String resourceId, @RequestParam String targetParentId) {
        ResourceInfo current = resourceInfoService.getResourceInfoByResourceId(resourceId);
        if (current == null) {
            throw new BusinessException("资源不存在");
        }

        // 不能移动到自身
        if (resourceId.equals(targetParentId)) {
            throw new BusinessException("不能移动到自身");
        }

        // 如果是目录，不能移动到自己的子目录下
        if (current.getNodeType() != null && current.getNodeType() == 1) {
            if (isDescendant(resourceId, targetParentId)) {
                throw new BusinessException("不能将目录移动到其子目录下");
            }
        }

        // 防止同级重名
        ResourceInfoQuery dupQuery = new ResourceInfoQuery();
        dupQuery.setParentId(targetParentId);
        dupQuery.setResourceName(current.getResourceName());
        List<ResourceInfo> dupList = resourceInfoService.findListByParam(dupQuery);
        if (dupList != null && !dupList.isEmpty()) {
            boolean hasConflict = dupList.stream().anyMatch(d -> !d.getResourceId().equals(resourceId));
            if (hasConflict) {
                throw new BusinessException("目标位置已存在同名资源");
            }
        }

        ResourceInfo updateBean = new ResourceInfo();
        updateBean.setParentId(targetParentId);
        updateBean.setUpdateTime(new Date());
        Integer result = resourceInfoService.updateResourceInfoByResourceId(updateBean, resourceId);
        return getSuccessResponseVO(result);
    }

    /**
     * 检查 candidate 是否为 ancestor 的后代
     */
    private boolean isDescendant(String ancestorId, String candidateId) {
        List<ResourceInfo> allDirs = loadAllDirs();
        String current = candidateId;
        while (current != null && !"0".equals(current)) {
            if (current.equals(ancestorId)) return true;
            String finalCurrent = current;
            ResourceInfo parent = allDirs.stream()
                    .filter(d -> d.getResourceId().equals(finalCurrent))
                    .findFirst().orElse(null);
            current = parent != null ? parent.getParentId() : "0";
        }
        return false;
    }

    /**
     * 解析存储路径为绝对路径（兼容旧的绝对路径和新的相对路径）
     */
    /**
     * 根据文件名后缀返回 MIME 类型
     */
    private String getMimeType(String fileName) {
        if (fileName == null) return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        String name = fileName.toLowerCase();
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".gif")) return "image/gif";
        if (name.endsWith(".bmp")) return "image/bmp";
        if (name.endsWith(".webp")) return "image/webp";
        if (name.endsWith(".svg")) return "image/svg+xml";
        if (name.endsWith(".pdf")) return "application/pdf";
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    private Path resolvePath(String storedPath) {
        if (storedPath == null || storedPath.isEmpty()) return null;
        Path path = Path.of(storedPath);
        if (path.isAbsolute()) return path;
        return Path.of(projectFolder, storedPath);
    }

    private List<ResourceInfo> loadAllDirs() {
        ResourceInfoQuery query = new ResourceInfoQuery();
        query.setNodeType(1);
        return resourceInfoService.findListByParam(query);
    }

    /**
     * 批量更新同级排序
     */
    @PostMapping("/sortResources")
    public ResponseVO<Void> sortResources(@RequestBody List<String> resourceIds) {
        for (int i = 0; i < resourceIds.size(); i++) {
            ResourceInfo bean = new ResourceInfo();
            bean.setSortOrder(i + 1);
            bean.setUpdateTime(new Date());
            resourceInfoService.updateResourceInfoByResourceId(bean, resourceIds.get(i));
        }
        return getSuccessResponseVO(null);
    }
}
