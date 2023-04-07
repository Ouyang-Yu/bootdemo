package 代码积累库;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-07-03  15:28
 */
public class Json处理 {

    private final String json = "{\"name\":\"zhangsan\",\"age\":12}";
    ;

    @Test
    public void ds() {


        Gson gson = new Gson();
        //将jsonStr转为Map
        Map<String, String> map = gson.fromJson(
                json,
                new TypeToken<Map<String, String>>() {}.getType()
        );
        map.forEach((k, v) -> System.out.println(k + "   :   " + v));
    }

    @SneakyThrows
    @Test
    public void jackson() {
        Person person = new ObjectMapper().readValue(json, Person.class);

    }

    @Data
    static
    class Person {
        private Integer age;
        private String name;
    }

}
