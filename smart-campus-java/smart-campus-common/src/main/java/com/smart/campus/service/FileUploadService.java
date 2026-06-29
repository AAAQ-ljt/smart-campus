package com.smart.campus.service;

import com.smart.campus.entity.dto.UploadSession;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 初始化上传会话，保存文件信息到数据库和 Redis
     */
    UploadSession initUpload(String originalName, String fileExt, Long fileSize,
                             String mimeType, Integer resourceType, String parentId,
                             Integer totalChunks);

    /**
     * 处理单个分片
     * @return 是否是最后一片
     */
    boolean processChunk(String uploadId, int chunkIndex, MultipartFile chunk) throws Exception;

    /**
     * 合并分片文件
     */
    String mergeChunks(String uploadId) throws Exception;

    /**
     * 获取上传进度
     */
    UploadSession getUploadProgress(String uploadId);

    /**
     * 将文件加入处理队列
     */
    void enqueueProcessing(String uploadId);

    /**
     * 处理队列中的文件（视频转码、移动等）
     */
    void processFile(String uploadId) throws Exception;

    /**
     * 取消上传，清理 Redis 和临时文件
     */
    void cancelUpload(String uploadId);

    /**
     * 删除资源时清理磁盘文件和 Redis 会话
     */
    void cleanupResource(String resourceId, String resourcePath);
}
