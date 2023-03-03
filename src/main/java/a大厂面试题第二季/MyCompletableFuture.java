package a大厂面试题第二季;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author Ouyang
 * @date 2022-04-19  16:48
 */
public class                       MyCompletableFuture {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() + 1,
            10,
            1000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<Void> future1 = CompletableFuture.runAsync(
                        () -> System.out.println("task 1: subThread execute" + Thread.currentThread().getName()),
                        executor
                )
                .whenComplete(new BiConsumer<Void, Throwable>() {
                    @Override
                    public void accept(Void unused, Throwable throwable) {

                        System.out.println(throwable.getMessage());
                    }
                })
                .exceptionally(throwable -> {
                    System.out.println(throwable.getMessage());
                    return null;
                }) //execute if exception occur

                .handleAsync(new BiFunction<Void, Throwable, Void>() {
                    @Override
                    public Void apply(Void unused, Throwable throwable) {
                        return null;
                    }
                });


        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("task 2: subThread execute" + Thread.currentThread().getName());
                    return 10;
                },
                executor
        );


        System.out.println("Main Thread End " + future2.get());


    }


}
