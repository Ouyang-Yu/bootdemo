package 代码积累库.配置类;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {
    @Bean(name = "mapperObject")
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper() {{
            registerModule(new JavaTimeModule()
                    // 序列化，写入输出流
                    .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))

                    // 反序列，读取输入流，只能反序列化放到Body里的json格式数据哦
                    .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
            );

            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略无法转换的对象
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);// 解析无成员变量的bean不会报异常
            configure(SerializationFeature.INDENT_OUTPUT, true);// PrettyPrinter 格式化输出
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            setTimeZone(TimeZone.getTimeZone("GMT+8:00")); // 指定时区
            setSerializationInclusion(JsonInclude.Include.NON_NULL);// null json只包括非null 不序列化
        }};
    }
}
