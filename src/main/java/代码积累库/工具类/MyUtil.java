package 代码积累库.工具类;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/18 11:33
 */
public class MyUtil {
    public static boolean isAnyFieldNull(Object obj) {
        Predicate<Field> predicate = new Predicate<>() {
            @SneakyThrows
            @Override
            public boolean test(Field field) {
                field.setAccessible(true);
                return field.get(obj) == null;
            }
        };


        return Arrays.stream(obj.getClass().getDeclaredFields())
                .anyMatch(field -> fieldValueNull(field, obj));

    }

    @SneakyThrows
    public static boolean fieldValueNull(Field field, Object object) {
        field.setAccessible(true);
        return field.get(object) == null;
    }
}
