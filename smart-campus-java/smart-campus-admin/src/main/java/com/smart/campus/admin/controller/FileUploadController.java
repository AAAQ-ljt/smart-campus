package com.smart.campus.admin.controller;

import com.smart.campus.controller.ABaseController;
import com.smart.campus.entity.dto.UploadSession;
import com.smart.campus.entity.vo.ResponseVO;
import com.smart.campus.service.FileUploadService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileUploadController extends ABaseController {

    @Resource
    private FileUploadService fileUploadService;

    /**
     * 初始化分片上传
     */
    @PostMapping("/initUpload")
    public ResponseVO<UploadSession> initUpload(
            @RequestParam String originalName,
            @RequestParam String fileExt,
            @RequestParam Long fileSize,
            @RequestParam(required = false) String mimeType,
            @RequestParam(defaultValue = "5") Integer resourceType,
            @RequestParam(defaultValue = "0") String parentId,
            @RequestParam Integer totalChunks) {
        UploadSession session = fileUploadService.initUpload(
                originalName, fileExt, fileSize, mimeType, resourceType, parentId, totalChunks);
        return getSuccessResponseVO(session);
    }

    /**
     * 上传单个分片
     */
    @PostMapping("/uploadChunk")
    public ResponseVO<Boolean> uploadChunk(
            @RequestParam String uploadId,
            @RequestParam int chunkIndex,
            @RequestParam MultipartFile chunk) {
        try {
            boolean isLast = fileUploadService.processChunk(uploadId, chunkIndex, chunk);
            return getSuccessResponseVO(isLast);
        } catch (Exception e) {
            return getServerErrorResponseVO(false);
        }
    }

    /**
     * 获取上传进度
     */
    @PostMapping("/uploadProgress")
    public ResponseVO<UploadSession> uploadProgress(@RequestParam String uploadId) {
        UploadSession session = fileUploadService.getUploadProgress(uploadId);
        return getSuccessResponseVO(session);
    }

    /**
     * 取消上传
     */
    @PostMapping("/cancelUpload")
    public ResponseVO<Integer> cancelUpload(@RequestParam String uploadId) {
        fileUploadService.cancelUpload(uploadId);
        return getSuccessResponseVO(1);
    }
}
