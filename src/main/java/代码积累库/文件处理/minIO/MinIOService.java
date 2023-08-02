package 代码积累库.文件处理.minIO;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/11 15:23
 */
@RequiredArgsConstructor
public class MinIOService {
    final MinioClient minioClient;

    @SneakyThrows
    @Test
    public void upload(byte[] bytes, String folder, String filename) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("bucket")
                        .object("")
                        .stream(stream, stream.available(), -1)
                        .contentType("application/octet-stream")
                        .build()
        );
    }
}
