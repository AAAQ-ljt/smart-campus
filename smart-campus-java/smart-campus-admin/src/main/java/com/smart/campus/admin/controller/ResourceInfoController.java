package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.po.ResourceInfo;
import com.smart.campus.entity.query.ResourceInfoQuery;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.exception.BusinessException;
import com.smart.campus.service.ResourceInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resourceInfo")
public class ResourceInfoController extends ABaseController {

    @Resource
    private ResourceInfoService resourceInfoService;

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
        return getSuccessResponseVO(result);
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
