package 代码积累库.文件处理.minIO;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/11 15:47
 */
@RestController
public class minIOController {

    @Resource
    MinIOService minIOService;

    @PostMapping(value = "upload/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadVideo(
            @RequestPart MultipartFile file,
            @RequestParam String folder,
            @RequestParam String filename
    ) throws IOException {
        minIOService.upload(file.getBytes(), folder, filename);
        return "yes";
    }

}
