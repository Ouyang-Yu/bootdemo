package 代码积累库;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
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
                        .orElseGet(() -> color -> color)//如果没有函数可以apply那就apply一个废话函数
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
        //方法作为类的实例
        executeIfParamNotNull(System.out::println, new Random().nextBoolean() ? 1 : null);
        executeIfParamNotNull.accept(System.out::println, new Random().nextBoolean() ? 1 : null);
        //用高阶函数优点是不同起临时变量,更优雅更装逼
        Integer integer = new Random().nextBoolean() ? 1 : null;
        if (integer != null) {
            System.out.println(integer);
        }
    }
}
