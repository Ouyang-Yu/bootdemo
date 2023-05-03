package 代码积累库.工具类;

import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.SerializationUtils;

public class SpringUtils {


    @Test
    public void serialise() {
        byte[] serialize = SerializationUtils.serialize(new Object());
    }

    @Test
    public void reflex() {
        ClassUtils.getPackageName(String.class);
    }

    @Test
    public void code() {
        byte[] bytes = DigestUtils.md5Digest("123456".getBytes());
    }
}
