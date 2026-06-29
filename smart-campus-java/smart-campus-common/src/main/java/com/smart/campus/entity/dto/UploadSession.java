package com.smart.campus.entity.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Redis 中存储的上传会话
 */
public class UploadSession implements Serializable {

    private String uploadId;
    private String resourceId;
    private String originalName;
    private String fileExt;
    private Long fileSize;
    private String mimeType;
    private Integer resourceType;
    private String parentId;
    private Integer totalChunks;
    private Integer uploadedChunks;
    private Integer status; // 1-上传中, 2-合并中, 3-成功, 5-失败
    private Date createTime;

    public String getUploadId() { return uploadId; }
    public void setUploadId(String uploadId) { this.uploadId = uploadId; }

    public String getResourceId() { return resourceId; }
    public void setResourceId(String resourceId) { this.resourceId = resourceId; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public String getFileExt() { return fileExt; }
    public void setFileExt(String fileExt) { this.fileExt = fileExt; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public Integer getResourceType() { return resourceType; }
    public void setResourceType(Integer resourceType) { this.resourceType = resourceType; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public Integer getTotalChunks() { return totalChunks; }
    public void setTotalChunks(Integer totalChunks) { this.totalChunks = totalChunks; }

    public Integer getUploadedChunks() { return uploadedChunks; }
    public void setUploadedChunks(Integer uploadedChunks) { this.uploadedChunks = uploadedChunks; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
