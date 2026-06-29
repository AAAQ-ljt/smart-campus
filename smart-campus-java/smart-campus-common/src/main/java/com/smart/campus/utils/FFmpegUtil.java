package com.smart.campus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * FFmpeg 视频/图片处理工具类
 */
public class FFmpegUtil {

    private static final Logger log = LoggerFactory.getLogger(FFmpegUtil.class);

    /** HLS 分片时长（秒） */
    public static final String HLS_SEGMENT_TIME = "10";
    /** ffmpeg 命令 */
    private static final String FFMPEG_CMD = "ffmpeg";
    /** ffprobe 命令 */
    private static final String FFPROBE_CMD = "ffprobe";

    /**
     * 生成 HLS 分片（m3u8 + ts）
     *
     * @param sourcePath 源视频路径
     * @param hlsDir     HLS 输出目录
     * @param baseName   输出文件基础名
     * @return ffmpeg 进程退出码
     */
    public static int generateHLS(String sourcePath, String hlsDir, String baseName) {
        try {
            String hlsOutput = hlsDir + File.separator + baseName + ".m3u8";
            String tsPattern = hlsDir + File.separator + baseName + "_%05d.ts";
            ProcessBuilder pb = new ProcessBuilder(
                    FFMPEG_CMD, "-i", sourcePath,
                    "-c:v", "libx264", "-c:a", "aac",
                    "-f", "hls", "-hls_time", HLS_SEGMENT_TIME, "-hls_list_size", "0",
                    "-hls_segment_filename", tsPattern,
                    hlsOutput
            );
            pb.redirectErrorStream(true);
            Process p = pb.start();
            readProcessOutput(p);
            int exit = p.waitFor();
            log.info("HLS 转码完成: baseName={}, exit={}", baseName, exit);
            return exit;
        } catch (Exception e) {
            log.error("HLS 转码失败: baseName={}", baseName, e);
            return -1;
        }
    }

    /**
     * 从视频中提取封面图
     *
     * @param sourcePath 源视频路径
     * @param coverPath  封面输出路径
     */
    public static void generateCover(String sourcePath, String coverPath) {
        try {
            // 先尝试第1秒，-update 1 确保输出单张图片
            ProcessBuilder pb = new ProcessBuilder(
                    FFMPEG_CMD, "-i", sourcePath, "-ss", "00:00:01", "-frames:v", "1", "-update", "1", coverPath, "-y"
            );
            pb.redirectErrorStream(true);
            Process p = pb.start();
            readProcessOutput(p);
            p.waitFor();

            // 失败则尝试第0秒
            if (!new File(coverPath).exists()) {
                new ProcessBuilder(FFMPEG_CMD, "-i", sourcePath, "-ss", "00:00:00", "-frames:v", "1", "-update", "1", coverPath, "-y")
                        .redirectErrorStream(true).start().waitFor();
            }
        } catch (Exception e) {
            log.error("封面提取失败: source={}", sourcePath, e);
        }
    }

    /**
     * 获取视频时长（秒）
     *
     * @param sourcePath 源视频路径
     * @return 视频时长（秒），失败返回 0
     */
    public static int getDuration(String sourcePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    FFPROBE_CMD, "-v", "error", "-show_entries", "format=duration",
                    "-of", "default=noprint_wrappers=1:nokey=1", sourcePath
            );
            pb.redirectErrorStream(true);
            Process p = pb.start();
            String output = new String(p.getInputStream().readAllBytes()).trim();
            p.waitFor();
            if (!output.isEmpty()) {
                return (int) Double.parseDouble(output);
            }
        } catch (Exception e) {
            log.warn("获取视频时长失败: {}", sourcePath, e);
        }
        return 0;
    }

    private static void readProcessOutput(Process p) {
        try {
            byte[] bytes = p.getInputStream().readAllBytes();
            if (bytes.length > 0) {
                log.debug(new String(bytes));
            }
        } catch (Exception ignored) {
            // 进程输出读取失败，不影响主流程
        }
    }
}
