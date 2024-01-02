package 代码积累库;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-29  11:11
 */
public class 函数式编程 {
    class A {

    }

    @Test
    public void functions() {
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class Camera {
            private Color color;

            @SafeVarargs
            public final Camera changed(Function<Color, Color>... functions) {
                this.color = Arrays.stream(functions)
                        .reduce(Function::compose)
                        .orElseThrow()
                        .apply(color);
                return this;//改变了原来的元素,不好,stream也没改变原来集合
            }
            @SafeVarargs
            public final Camera apply(Function<Color, Color>... functions) {
                Color apply = Arrays.stream(functions)
                        .reduce(Function::compose)
                        .orElseGet(() -> color -> color)//如果传null 那就返回一个变成自己的函数
                        .apply(this.color);
                return new Camera(apply);//不改变原来元素
            }
        }
        Camera camera = new Camera(new Color(93, 30, 155));
        System.out.println("camera.getColor() = " + camera.getColor());
        Camera apply = camera
                .apply(Color::brighter, Color::brighter)
                .apply()
                .apply(Color::darker);

        System.out.println(apply.getColor());

        System.out.println("camera = " + camera.getColor());


    }

    @Test
    public void a() {
        Function<Object, String> fun = String::valueOf;
        BiFunction<Object, Function<Object, String>, String> biFunction =
                (integer, transform) -> transform.apply(integer);

        String apply = biFunction.apply(new Object(), fun);
        String apply1 = fun.apply(new Object());
        System.out.println(apply);


    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {

        R apply(T t, U u, V v);
    }

    @Test
    public void multiParam() {
        TriFunction<Integer, Integer, Integer, String> triFunction = (a, b, c) -> a + b + c + "";

        String apply = triFunction.apply(1, 1, 1);
    }


    private <A> void executeIfParamNotNull(Consumer<A> consumer, A a) {
        if (a != null) {
            consumer.accept(a);
        }
    }

    public <A> void executeIfNotNull(Runnable runnable, A a) {
        if (a != null) {
            runnable.run();
        }
    }


    @Test
    public void f() {

        BiConsumer<Consumer<Object>, Object> executeIfParamNotNull = this::executeIfParamNotNull;
        // 方法作为类的实例
        executeIfParamNotNull(System.out::println, new Random().nextBoolean() ? 1 : null);
        executeIfParamNotNull.accept(System.out::println, new Random().nextBoolean() ? 1 : null);
        // 用高阶函数代替下面函数,优点是不同起临时变量,更优雅更装逼,用kt可以给话甚至可以给consumer起一个拓展函数
        Integer integer = new Random().nextBoolean() ? 1 : null;
        if (integer != null) {
            System.out.println(integer);
        }
    }

    public static <T, R> R retryFunction(Function<T, R> function, T param, int time) {
        while (true) {
            try {
                return function.apply(param);
            } catch (Exception e) {
                time--;
                if (time <= 0) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static <T, R> R cacheFunction(Function<T, R> function, T param, Map<T, R> cache) {
        R r = cache.get(param);
        if (r != null) {
            return r;
        }
        R result = function.apply(param);
        cache.put(param,result);
        return result;
    }
    public  <T, R> R logFunction(Function<T, R> function, T t, String logTitle) {


        Logger log = LoggerFactory.getLogger(this.getClass());
        long startTime = System.currentTimeMillis();

        log.info("param={},requestTime={}",
                t.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        R apply = function.apply(t);
        long endTime = System.currentTimeMillis();
        log.info("title={},return={},spendTime={}ms",
                logTitle,
                apply.toString(),
                endTime - startTime
        );
        return apply;
    }
    @Test
    public void logFun() {
        int s = logFunction(String::length, "123", "1");
    }


    public static void main(String[] args) {
        //http调用，失败会重试3次
       // retryFunction(()->http.call(),3);
        //把数字1转成数字 失败会重试三次
        String s = retryFunction(String::valueOf, 1, 3);
        String ss = retryFunction(String::valueOf, 1, 3);

        cacheFunction(String::valueOf, 2, new HashMap<>());
    }



}
