package com.ouyang.boot.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-24 13:26
 */
@RestController
public class 单文件上传 { //默认配置单个文件大小最大1MB 请求最大10MB
    private final static String UPLOAD_PATH = "";

    @SneakyThrows
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "upload failed";
        }
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        Files.write(
                Paths.get(UPLOAD_PATH +
                        new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date()) +
                        new Random().nextInt(100) +
                        fileName.substring(fileName.lastIndexOf("."))
                ),//新的文件路径  文件夹+时间+随机数+原文件后缀
                file.getBytes()
        );
        return "upload succeed";
    }
}
