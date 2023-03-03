package OnJava;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-23 15:39
 */
public class _5_10_做蛋糕 {
    static class Batter {
        static class Eggs {
        }

        static class Milk {
        }
    }

    <T> CompletableFuture<T> prepare(T ingredient) {
        return CompletableFuture.completedFuture(ingredient)
                .thenApplyAsync(new Function<T, T>() {
                    @SneakyThrows
                    @Override
                    public T apply(T t) {
                        Thread.sleep(100);
                        return t;
                    }
                });
    }

    CompletableFuture<Batter> mix() {
        CompletableFuture.allOf(
                prepare(new Batter.Eggs()),
                prepare(new Batter.Milk()))
                .join();
        return CompletableFuture.completedFuture(new Batter());
    }


    CompletableFuture bake(CompletableFuture<Batter> batterFuture) {
        return batterFuture.thenApplyAsync(new Function<Batter, Object>() {
            @Override
            public Object apply(Batter batter) {
                return batter;
            }
        });
    }

    @Test
    public void test() {
        Stream.of(bake(mix()), bake(mix()))
                .forEach(
                        baked -> baked.
                                thenComposeAsync((it) -> new Batter())
                                .thenAcceptAsync(System.out::println)
                                .join()
                );

    }
}
