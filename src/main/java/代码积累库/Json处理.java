package 代码积累库;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-07-03  15:28
 */
public class Json处理 {
    @Test
    public void ds() {

        String jsonStr = "{\"name\":\"zhangsan\",\"age\":12}";
        Gson gson = new Gson();
        //将jsonStr转为Map
        Map<String, String> map = gson.fromJson(
                jsonStr,
                new TypeToken<Map<String, String>>() {}.getType()
        );
        map.forEach((k, v) -> System.out.println(k + "   :   " + v));
    }
}
