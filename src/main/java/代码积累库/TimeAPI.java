package 代码积累库;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-08 16:51
 */
public class TimeAPI {
    @Test
    public void first() {
        LocalDate first = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        System.out.println(first);

    }
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("EEE, MMM d yyyy, KK:mm a");
        System.out.println(now); //2022-05-08T16:34:17.744841800
        System.out.println(inFormat.format(now));//   2022/05/08 16:52:38
        System.out.println(outFormat.format(now)); // 周日, 5月 8 2022, 04:34 下午

        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());  // getMouth()返回月份英文简写
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());

        String format1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    @Test
    public void d() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //HH24小时
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("EEE, MMM d yyyy, KK:mm a");
        System.out.println(now); //2022-05-08T16:34:17.744841800
        System.out.println(inFormat.format(now));//2022/05/08 16:52:38
        System.out.println(outFormat.format(now)); //周日, 5月 8 2022, 04:34 下午

    }

    @Test
    private void data转化() {
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime dateTime = nowDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 定义一个ObjectMapper的Bean交给Spring注入到Jackson
     */
    @Bean(name = "mapperObject")
    public ObjectMapper getObjectMapper() {
        SimpleModule module = new JavaTimeModule()
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                //只能反序列化放到Body里的json格式数据,请求参数里的不归json管,请用@DateTimeFormat
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ObjectMapper().registerModule(module).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConvert() {
        return source -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    //将LocalDateTime字段以时间戳的方式返回给前端
//LocalDateTime字段上添加@JsonSerialize(using = LocalDateTimeConverter.class)注解

    class LocalDateTimeConverter extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(value.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        }
    }
    @RestControllerAdvice
    static
    class ExceptionResolver {
        @InitBinder
        public void initBinder(WebDataBinder webDataBinder) {
            webDataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
                @Override
                public void setAsText(String text) throws IllegalArgumentException {
                    setValue(LocalDate.parse(text, DateTimeFormatter.ISO_DATE));
                }
            });
            webDataBinder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
                @Override
                public void setAsText(String text) throws IllegalArgumentException {
                    setValue(LocalTime.parse(text, DateTimeFormatter.ISO_TIME));
                }
            });
            webDataBinder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
                @Override
                public void setAsText(String text) throws IllegalArgumentException {
                    setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            });

        }
    }
}


