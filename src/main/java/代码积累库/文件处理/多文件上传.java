package 代码积累库.文件处理;

import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-24 15:15
 */
@RestController
public class 多文件上传 {
    @Resource
    private StandardServletMultipartResolver servletMultipartResolver;

    @SneakyThrows
    @PostMapping("/multiUpload")
    public String multiUpload(HttpServletRequest request) {
        if (!servletMultipartResolver.isMultipart(request)) {
            return "not file request";  // 不是文件上传的请求
        }
        Collection<MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap().values();
        // 取出request中的文件
        if (CollectionUtils.isEmpty(files)) {
            return "no file";
        }
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                continue; // 如果文件名为空就遗弃掉,继续下一个
            }
            Files.write(
                    Paths.get("UPLOAD_PATH"
                            + new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date()) +
                            new Random().nextInt(100) +
                            fileName.substring(fileName.lastIndexOf("."))
                    ),// 新的文件路径  文件夹+时间+随机数+原文件后缀
                    file.getBytes()
            );
        }
        return "upload succeed";
    }


    @PostMapping("files")
    public void files(HttpServletRequest request) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            part.write(getFileName(part));
            //
            // spring.servlet.multipart.location=
        }
    }

    private String getFileName(Part part) {
        for (String s : part.getHeader("content-disposition").split(";")) {
            // form-data; name="upfile"; filename="a.gif"
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
