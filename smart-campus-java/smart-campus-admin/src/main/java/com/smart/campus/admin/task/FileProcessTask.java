package com.smart.campus.admin.task;

import com.smart.campus.service.FileUploadService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class FileProcessTask {

    private static final Logger log = LoggerFactory.getLogger(FileProcessTask.class);
    private static final String REDIS_QUEUE_KEY = "upload:process:queue";
    private static final String REDIS_KEY_PREFIX = "upload:session:";

    /** 队列阻塞超时（秒），设一个较大值避免频繁超时 */
    private static final long BRPOP_TIMEOUT_SECONDS = 30;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private FileUploadService fileUploadService;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private volatile boolean running = true;
    private Thread consumerThread;

    @PostConstruct
    public void start() {
        consumerThread = new Thread(() -> {
            log.info("文件处理队列消费者启动");
            while (running) {
                try {
                    String uploadId = stringRedisTemplate.opsForList()
                            .rightPop(REDIS_QUEUE_KEY, BRPOP_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                    if (uploadId == null || uploadId.isEmpty()) {
                        continue;
                    }
                    // 检查是否已被取消
                    if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(REDIS_KEY_PREFIX + uploadId))) {
                        log.info("上传已取消，跳过处理: {}", uploadId);
                        continue;
                    }
                    String taskId = uploadId;
                    executor.submit(() -> {
                        try {
                            log.info("开始处理文件: {}", taskId);
                            fileUploadService.processFile(taskId);
                            log.info("文件处理完成: {}", taskId);
                        } catch (Exception e) {
                            log.error("文件处理失败: {}", taskId, e);
                        }
                    });
                } catch (Exception e) {
                    log.error("队列消费异常", e);
                    // 短暂休眠避免异常时疯狂重试
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                }
            }
            log.info("文件处理队列消费者退出");
        }, "file-process-consumer");
        consumerThread.setDaemon(true);
        consumerThread.start();
    }

    @PreDestroy
    public void shutdown() {
        running = false;
        if (consumerThread != null) {
            consumerThread.interrupt();
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
