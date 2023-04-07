package com.ouyang.boot.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-24 13:26
 */
@RestController
public class 单文件上传 { //默认配置单个文件大小最大1MB 请求最大10MB
    private final static String UPLOAD_PATH = "D/img";

    @SneakyThrows
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "upload failed";
        }
        File dir = new File(UPLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String newFileName = UPLOAD_PATH +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                new Random().nextInt(100) +
                fileName.substring(fileName.lastIndexOf("."));
        //新的文件路径  文件夹+时间+随机数+原文件后缀
        Files.write(Paths.get(newFileName), file.getBytes());
       //or file.transferTo(new File(newFileName));
        return MessageFormat.format("upload succeed {0}",newFileName);
    }
}
