package 代码积累库;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-07-03  15:28
 */
public class Json处理 {

    private final String json = "{\"name\":\"zhangsan\",\"age\":12}";



    @Test
    public void gson() {


        Gson gson = new Gson();
         Person person = gson.fromJson(json, Person.class);


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
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(json, Person.class);
    }

    @Test
    public void 数组类型的json转list() throws JsonProcessingException {
        List<Object> objects = new ObjectMapper().readValue("json", new TypeReference<List<Object>>() {});
    }


    @Data
    static
    class Person {
        private Integer age;
        private String name;
    }

}
