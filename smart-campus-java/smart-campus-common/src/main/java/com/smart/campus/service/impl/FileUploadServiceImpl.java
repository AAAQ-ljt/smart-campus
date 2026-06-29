package com.smart.campus.service.impl;

import com.alibaba.fastjson2.JSON;
import com.smart.campus.controller.LoginContextHolder;
import com.smart.campus.entity.dto.UploadSession;
import com.smart.campus.entity.po.ResourceInfo;
import com.smart.campus.mappers.ResourceInfoMapper;
import com.smart.campus.entity.query.ResourceInfoQuery;
import com.smart.campus.service.FileUploadService;
import com.smart.campus.utils.FFmpegUtil;
import jakarta.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    // ==================== Redis 常量 ====================
    private static final String REDIS_KEY_PREFIX = "upload:session:";
    private static final String REDIS_QUEUE_KEY = "upload:process:queue";
    private static final String REDIS_COUNTER_SUFFIX = ":chunks";
    private static final int REDIS_TTL_HOURS = 24;

    // ==================== 状态常量 ====================
    /** 上传中 */
    private static final int STATUS_UPLOADING = 1;
    /** 合并中 */
    private static final int STATUS_MERGING = 2;
    /** 上传成功 */
    private static final int STATUS_SUCCESS = 3;
    /** 上传失败 */
    private static final int STATUS_FAILED = 5;

    // ==================== 业务常量 ====================
    /** 文件节点类型 */
    private static final int NODE_TYPE_FILE = 2;
    /** 默认父级 ID */
    private static final String DEFAULT_PARENT_ID = "0";
    /** 视频资源类型 */
    private static final int RESOURCE_TYPE_VIDEO = 1;
    /** 分片文件名格式 */
    private static final String CHUNK_FILE_FORMAT = "chunk_%06d";
    /** 月份目录格式 */
    private static final String MONTH_FORMAT = "yyyyMM";
    /** HLS 输出子目录 */
    private static final String HLS_SUBDIR = "hls";
    /** 封面文件名 */
    private static final String COVER_FILE_NAME = "cover.jpg";
    /** 合并文件名格式 */
    private static final String MERGED_FILE_FORMAT = "%s%s";

    @Value("${project.folder:./}")
    private String projectFolder;

    @Value("${resource.temp-dir:resource/temp}")
    private String tempDir;

    @Value("${resource.file-dir:resource/files}")
    private String fileDir;

    @Resource
    private ResourceInfoMapper<ResourceInfo, ResourceInfoQuery> resourceInfoMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public UploadSession initUpload(String originalName, String fileExt, Long fileSize,
                                     String mimeType, Integer resourceType, String parentId,
                                     Integer totalChunks) {
        // 保存到数据库
        ResourceInfo info = new ResourceInfo();
        String resourceId = UUID.randomUUID().toString().replace("-", "");
        info.setResourceId(resourceId);
        info.setResourceName(originalName);
        info.setNodeType(NODE_TYPE_FILE);
        info.setParentId(parentId != null ? parentId : DEFAULT_PARENT_ID);
        info.setResourceType(resourceType);
        info.setOriginalName(originalName);
        info.setFileSize(fileSize);
        info.setFileExt(fileExt);
        info.setMimeType(mimeType);
        info.setStatus(STATUS_UPLOADING);
        info.setDuration(0);
        Integer currentUserId = LoginContextHolder.getCurrentUserId();
        if (currentUserId != null) {
            info.setUploadUserId(currentUserId);
        } else {
            log.warn("上传时未获取到当前登录用户，uploadUserId 为空。请检查 adminToken 是否有效");
        }
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());

        // 计算 sortOrder
        ResourceInfoQuery sq = new ResourceInfoQuery();
        sq.setParentId(info.getParentId());
        sq.setNodeType(NODE_TYPE_FILE);
        sq.setOrderBy("sort_order desc");
        List<ResourceInfo> siblings = resourceInfoMapper.selectList(sq);
        info.setSortOrder((siblings == null || siblings.isEmpty()) ? 1 :
                (siblings.get(0).getSortOrder() == null ? 0 : siblings.get(0).getSortOrder()) + 1);

        resourceInfoMapper.insert(info);

        // insert 中的 <selectKey> 可能覆盖 resourceId，因此必须用 info.getResourceId() 获取最终入库的值
        String actualResourceId = info.getResourceId();

        // 保存到 Redis
        UploadSession session = new UploadSession();
        session.setUploadId(actualResourceId);
        session.setResourceId(actualResourceId);
        session.setOriginalName(originalName);
        session.setFileExt(fileExt);
        session.setFileSize(fileSize);
        session.setMimeType(mimeType);
        session.setResourceType(resourceType);
        session.setParentId(info.getParentId());
        session.setTotalChunks(totalChunks);
        session.setUploadedChunks(0);
        session.setStatus(STATUS_UPLOADING);
        session.setCreateTime(new Date());

        stringRedisTemplate.opsForValue().set(REDIS_KEY_PREFIX + actualResourceId,
                JSON.toJSONString(session), REDIS_TTL_HOURS, TimeUnit.HOURS);

        // 初始化分片计数器（原子递增用）
        stringRedisTemplate.opsForValue().set(REDIS_KEY_PREFIX + actualResourceId + REDIS_COUNTER_SUFFIX,
                "0", REDIS_TTL_HOURS, TimeUnit.HOURS);

        // 创建临时目录
        try {
            Files.createDirectories(Path.of(getTempPath(actualResourceId)));
        } catch (IOException e) {
            log.error("创建临时目录失败: uploadId={}", actualResourceId, e);
        }

        return session;
    }

    @Override
    public boolean processChunk(String uploadId, int chunkIndex, MultipartFile chunk) throws Exception {
        String tempPath = getTempPath(uploadId);
        Path chunkFile = Path.of(tempPath, String.format(CHUNK_FILE_FORMAT, chunkIndex));
        chunk.transferTo(chunkFile.toFile());

        // 原子递增分片计数，避免并发读写导致的计数丢失
        String counterKey = REDIS_KEY_PREFIX + uploadId + REDIS_COUNTER_SUFFIX;
        Long uploadedChunks = stringRedisTemplate.opsForValue().increment(counterKey);
        stringRedisTemplate.expire(counterKey, REDIS_TTL_HOURS, TimeUnit.HOURS);

        String key = REDIS_KEY_PREFIX + uploadId;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            log.error("上传会话不存在: uploadId={}", uploadId);
            throw new RuntimeException("上传会话不存在");
        }
        UploadSession session = JSON.parseObject(json, UploadSession.class);
        session.setUploadedChunks(uploadedChunks.intValue());
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(session), REDIS_TTL_HOURS, TimeUnit.HOURS);

        boolean isLast = uploadedChunks >= session.getTotalChunks();
        if (isLast && session.getStatus() == STATUS_UPLOADING) {
            session.setStatus(STATUS_MERGING);
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(session), REDIS_TTL_HOURS, TimeUnit.HOURS);
            enqueueProcessing(uploadId);
        }
        return isLast;
    }

    @Override
    public String mergeChunks(String uploadId) throws Exception {
        String tempPath = getTempPath(uploadId);
        Path tempDirPath = Path.of(tempPath);

        String key = REDIS_KEY_PREFIX + uploadId;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            log.error("上传会话不存在，无法合并: uploadId={}", uploadId);
            throw new RuntimeException("上传会话不存在");
        }
        UploadSession session = JSON.parseObject(json, UploadSession.class);

        String mergedFileName = String.format(MERGED_FILE_FORMAT, uploadId, session.getFileExt());
        Path mergedFile = tempDirPath.resolve(mergedFileName);

        try (FileOutputStream fos = new FileOutputStream(mergedFile.toFile())) {
            for (int i = 0; i < session.getTotalChunks(); i++) {
                Path chunkFile = tempDirPath.resolve(String.format(CHUNK_FILE_FORMAT, i));
                if (Files.exists(chunkFile)) {
                    Files.copy(chunkFile, fos);
                }
            }
        }

        // 删除分片文件
        File[] chunks = tempDirPath.toFile().listFiles(f -> f.getName().startsWith("chunk_"));
        if (chunks != null) {
            for (File f : chunks) {
                if (!f.delete()) {
                    log.warn("删除分片文件失败: {}", f.getAbsolutePath());
                }
            }
        }

        // 更新数据库：存储临时路径（相对路径）
        ResourceInfo updateBean = new ResourceInfo();
        updateBean.setResourcePath(toRelativePath(mergedFile.toString()));
        updateBean.setUpdateTime(new Date());
        resourceInfoMapper.updateByResourceId(updateBean, uploadId);

        return mergedFile.toString();
    }

    @Override
    public UploadSession getUploadProgress(String uploadId) {
        String json = stringRedisTemplate.opsForValue().get(REDIS_KEY_PREFIX + uploadId);
        if (json == null) return null;
        return JSON.parseObject(json, UploadSession.class);
    }

    @Override
    public void enqueueProcessing(String uploadId) {
        stringRedisTemplate.opsForList().leftPush(REDIS_QUEUE_KEY, uploadId);
    }

    @Override
    public void processFile(String uploadId) throws Exception {
        String key = REDIS_KEY_PREFIX + uploadId;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            log.warn("上传会话不存在，跳过处理: uploadId={}", uploadId);
            return;
        }
        UploadSession session = JSON.parseObject(json, UploadSession.class);

        // 先合并分片
        String mergedPath = mergeChunks(uploadId);
        Path mergedFile = Path.of(mergedPath);

        if (!Files.exists(mergedFile)) {
            log.error("合并文件不存在: {}", mergedFile);
            updateStatus(uploadId, STATUS_FAILED);
            return;
        }

        // 构建正式目录
        String month = new SimpleDateFormat(MONTH_FORMAT).format(new Date());
        String finalDir = Path.of(projectFolder, fileDir, month, uploadId).toString();
        Files.createDirectories(Path.of(finalDir));

        // 视频文件处理
        if (session.getResourceType() != null && session.getResourceType() == RESOURCE_TYPE_VIDEO) {
            processVideo(mergedFile.toString(), finalDir, uploadId, session);
        } else {
            // 非视频直接拷贝
            Path destFile = Path.of(finalDir, session.getOriginalName());
            Files.copy(mergedFile, destFile, StandardCopyOption.REPLACE_EXISTING);

            ResourceInfo updateBean = new ResourceInfo();
            updateBean.setResourcePath(toRelativePath(destFile.toString()));
            updateBean.setStatus(STATUS_SUCCESS);
            updateBean.setUpdateTime(new Date());
            resourceInfoMapper.updateByResourceId(updateBean, uploadId);
        }

        // 删除临时目录
        deleteDirectoryQuietly(Path.of(getTempPath(uploadId)).toFile());

        session.setStatus(STATUS_SUCCESS);
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(session), REDIS_TTL_HOURS, TimeUnit.HOURS);
    }

    @Override
    public void cancelUpload(String uploadId) {
        updateStatus(uploadId, STATUS_FAILED);

        stringRedisTemplate.delete(REDIS_KEY_PREFIX + uploadId);
        stringRedisTemplate.delete(REDIS_KEY_PREFIX + uploadId + REDIS_COUNTER_SUFFIX);

        deleteDirectoryQuietly(Path.of(getTempPath(uploadId)).toFile());

        stringRedisTemplate.opsForList().remove(REDIS_QUEUE_KEY, 0, uploadId);
    }

    @Override
    public void cleanupResource(String resourceId, String resourcePath) {
        stringRedisTemplate.delete(REDIS_KEY_PREFIX + resourceId);
        stringRedisTemplate.delete(REDIS_KEY_PREFIX + resourceId + REDIS_COUNTER_SUFFIX);
        stringRedisTemplate.opsForList().remove(REDIS_QUEUE_KEY, 0, resourceId);

        if (resourcePath != null && !resourcePath.isEmpty()) {
            String absolutePath = toAbsolutePath(resourcePath);
            Path parentDir = Path.of(absolutePath).getParent();
            if (parentDir != null && Files.exists(parentDir)) {
                deleteDirectoryQuietly(parentDir.toFile());
            }
        }

        Path tempPath = Path.of(getTempPath(resourceId));
        if (Files.exists(tempPath)) {
            deleteDirectoryQuietly(tempPath.toFile());
        }
    }

    // ==================== 路径转换 ====================

    /**
     * 绝对路径转为相对路径（相对于 projectFolder）
     */
    private String toRelativePath(String absolutePath) {
        Path base = Path.of(projectFolder).toAbsolutePath().normalize();
        Path full = Path.of(absolutePath).toAbsolutePath().normalize();
        Path relative = base.relativize(full);
        return relative.toString().replace("\\", "/");
    }

    /**
     * 转为绝对路径：已是绝对路径直接返回，否则拼接 projectFolder
     */
    private String toAbsolutePath(String storedPath) {
        if (storedPath == null || storedPath.isEmpty()) return storedPath;
        Path path = Path.of(storedPath);
        if (path.isAbsolute()) return storedPath;
        return Path.of(projectFolder, storedPath).toString();
    }

    // ==================== 私有方法 ====================

    private void processVideo(String sourcePath, String finalDir, String uploadId, UploadSession session) {
        try {
            String hlsDir = Path.of(finalDir, HLS_SUBDIR).toString();
            Files.createDirectories(Path.of(hlsDir));

            // HLS 转码
            int hlsExit = FFmpegUtil.generateHLS(sourcePath, hlsDir, uploadId);

            // 封面提取
            String coverPath = Path.of(finalDir, COVER_FILE_NAME).toString();
            FFmpegUtil.generateCover(sourcePath, coverPath);

            // 获取时长
            int duration = FFmpegUtil.getDuration(sourcePath);

            // 拷贝原始文件
            Path rawFile = Path.of(finalDir, session.getOriginalName());
            Files.copy(Path.of(sourcePath), rawFile, StandardCopyOption.REPLACE_EXISTING);

            // 更新数据库（存储相对路径，封面不存在则不设置）
            ResourceInfo updateBean = new ResourceInfo();
            updateBean.setResourcePath(toRelativePath(rawFile.toString()));
            if (Files.exists(Path.of(coverPath))) {
                updateBean.setCoverUrl(toRelativePath(coverPath));
            }
            updateBean.setDuration(duration);
            updateBean.setStatus(hlsExit == 0 ? STATUS_SUCCESS : STATUS_FAILED);
            updateBean.setUpdateTime(new Date());
            resourceInfoMapper.updateByResourceId(updateBean, uploadId);

            if (hlsExit != 0) {
                session.setStatus(STATUS_FAILED);
            }
        } catch (Exception e) {
            log.error("视频处理失败: uploadId={}", uploadId, e);
            updateStatus(uploadId, STATUS_FAILED);
            session.setStatus(STATUS_FAILED);
        }
    }

    private void updateStatus(String uploadId, int status) {
        ResourceInfo bean = new ResourceInfo();
        bean.setStatus(status);
        bean.setUpdateTime(new Date());
        resourceInfoMapper.updateByResourceId(bean, uploadId);
    }

    private String getTempPath(String uploadId) {
        return Path.of(projectFolder, tempDir, uploadId).toString();
    }

    private void deleteDirectoryQuietly(File dir) {
        try {
            FileUtils.deleteDirectory(dir);
        } catch (Exception ignored) {
            // 清理失败不影响主流程
        }
    }
}
