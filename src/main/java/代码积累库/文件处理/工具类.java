package 代码积累库.文件处理;

import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Base64;

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @dateTime 2023/5/12 10:03
 */
public class 工具类 {
    @Test
    public void d() {
        System.out.println(MessageFormat.format("{0}", 20000000));
        System.out.printf("%d%n", 20000000);

    }

    @Test
    public void FileBySpring() throws IOException {
        // 文件转字节数组
        byte[] bytes = FileCopyUtils.copyToByteArray(new File(".gitignore"));
        String picBase64 = Base64.getEncoder().encodeToString(
                FileCopyUtils.copyToByteArray(new File(".gitignore"))
        );

        byte[] st = FileCopyUtils.copyToByteArray(
                Files.newInputStream(new File(".gitignore").toPath())
                // new FileInputStream(new File(".gitignore"))
        );
        System.out.println(new String(bytes, Charset.defaultCharset()));


        // 从reader (输入流)中读取str
        String gitignore = FileCopyUtils.copyToString(new InputStreamReader(Files.newInputStream(Paths.get("gitignore"))));

        // 文件拷贝
        FileCopyUtils.copy(
                new File(".gitignore"),
                new File("gitignore.txt")
        );


        // StreamUtils
    }
}
