package 代码积累库.springmvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Component
@Configuration
@SuppressWarnings("NullableProblems")
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = new ObjectMapper() {{
            registerModule(new SimpleModule() {{
                addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));


                addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }});
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略无法转换的对象
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);// 解析无成员变量的bean不会报异常
            configure(SerializationFeature.INDENT_OUTPUT, true);// PrettyPrinter 格式化输出
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault())); // 指定时区or "GMT+8:00"
            setSerializationInclusion(JsonInclude.Include.NON_NULL);// null json只包括非null 不序列化

        }};
        converters.set(0, new MappingJackson2HttpMessageConverter(objectMapper));
        // 将自己配置的jacksonConverter放到第一个
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 全局的@DateTimeFormat,解析str入参为LocalDateTime
     *
     * @param registry
     */
    @Override
    protected void addFormatters(FormatterRegistry registry) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        registry.addFormatter(new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, formatter);
            }


            @Override
            public String print(LocalDateTime object, Locale locale) {
                return formatter.format(object);
            }
        });
    }

}
