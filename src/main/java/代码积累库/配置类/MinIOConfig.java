package 代码积累库.配置类;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("")
                .credentials("api.minio", "")
                .build();
    }
}
