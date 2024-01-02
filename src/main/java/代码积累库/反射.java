package 代码积累库;

import com.google.common.base.Function;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class 反射 {
    @Test
    public void fun() throws NoSuchMethodException {
        Function<String, Integer> function = String::length;

        Method method = function.getClass().getMethods()[0];
        Method method1 = function.getClass().getDeclaredMethod("apply", Object.class);

        Arrays.stream(method.getParameterTypes()).forEach(System.out::println);
        System.out.println("method.getReturnType().getTypeName() = " + method.getReturnType().getTypeName());
        System.out.println("method.getReturnType().getName() = " + method.getReturnType().getName());
        Arrays.stream(method.getParameters()).forEach(System.out::println);


    }

    @Test
    public void 获取当前类名和方法名() {
        System.out.println(this.getClass().getName());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(new Exception().getStackTrace()[0].getMethodName());
        System.out.println(Object.class.getEnclosingMethod().getName());

    }

    @Test
    public void sd() {
        System.out.println(Arrays.toString(this.getClass().getMethods()));
    }


}
