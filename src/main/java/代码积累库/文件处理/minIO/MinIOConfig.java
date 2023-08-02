package 代码积累库.文件处理.minIO;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MinIOConfig {


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()// read config file
                .endpoint("http://localhost:9000")
                .credentials("admin", "admin")
                .build();
    }

}
