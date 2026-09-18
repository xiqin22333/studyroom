package org.example.studyroom.controller.common;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;
import org.example.studyroom.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 * 保存到外部目录 ./uploads/，由 WebMvcConfig 映射到 /upload/** 访问
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    /** 上传根目录（相对于项目启动目录） */
    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf('.')).toLowerCase();
        }
        // 仅允许图片类型
        if (!ext.matches("\\.(jpg|jpeg|png|gif|webp|bmp)$")) {
            return Result.error("仅支持 jpg/png/gif/webp/bmp 图片");
        }
        // 按日期分目录：uploads/2026/09/xxx.jpg
        String datePath = LocalDate.now().toString().replace('-', '/');
        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        try {
            Path dir = Paths.get(uploadDir, datePath).toAbsolutePath().normalize();
            Files.createDirectories(dir);
            File dest = new File(dir.toFile(), fileName);
            file.transferTo(dest);
            // 返回可访问 URL：/upload/2026/09/xxx.jpg
            String url = "/upload/" + datePath + "/" + fileName;
            return Result.success(url);
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
}
