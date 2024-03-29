package 代码积累库.文件处理;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class 单文件上传 { // 默认配置单个文件大小最大1MB 请求最大10MB
    private final static String UPLOAD_DIR = "D/img/";

    @SneakyThrows
    @PostMapping("/upload")
    public String upload(@RequestPart MultipartFile file) {
        if (file.isEmpty()) {
            return "upload failed";
        }

        String fileName = file.getOriginalFilename();

        assert fileName != null;
        String newFilePath = UPLOAD_DIR +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                new Random().nextInt(100) +
                fileName.substring(fileName.lastIndexOf("."));
        // 新的文件路径  文件夹+时间+随机数+原文件后缀


        Path dir = Paths.get(UPLOAD_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        // Path path = Paths.get(newFilePath);
        // if (!Files.exists(path.getParent())) {
        //     Files.createDirectories(path.getParent());
        // }
        // Files.write(Paths.get(newFilePath), file.getBytes(), StandardOpenOption.CREATE_NEW);

        file.transferTo(new File(newFilePath));
        return MessageFormat.format("upload succeed {0}", newFilePath);
    }
}
