package OnJava;

import lombok.SneakyThrows;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-22 21:13
 */
public class _5_8任务 {


    @SneakyThrows
    @Test
    public void test() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10)

                // .mapToObj(NapTask::new)
                .mapToObj(value ->
                        (Runnable) () -> System.out.println(value + Thread.currentThread().getName())
                )
                .forEach(executor::execute);


        System.out.println("主线程所有任务已提交");
        executor.shutdown();// main告诉线程池不再接受新的任务,做完已有的就停止,线程池没有结束之后,main才结束
        while (!executor.isTerminated()) {
            System.out.println(Thread.currentThread().getName() + "  正在运行");
            Thread.sleep(1000);

        }


    }

    @Test
    public void test1() {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 通过结构一致性支持lambda和方法引用
        Future<?> future = executor.submit(() -> System.out.println(1));// 在传入runnable时候和execute()一样
        Future<Integer> submit = executor.submit(() -> {
            System.out.println("");
            return 1;
        });  // 所有的submit都会返回一个future

    }

    AtomicBoolean running = new AtomicBoolean(true);

    @Test
    public void test2() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(value ->
                        (Runnable) () -> {
                            while (running.get()) {
                                sleepSecond(1);
                                System.out.println(Thread.currentThread().getName());
                            }
                            System.out.println(value);
                        }
                )
                .peek(threadPool::execute)
                .forEach(it -> running.set(false));
        threadPool.shutdown();
    }


    @Test
    public void test3() {
        AtomicBoolean running = new AtomicBoolean(true);

        IntStream.range(0, 10)
                .mapToObj(value -> (Runnable) () -> {
                    while (running.get()) {
                        sleepSecond(1);
                        System.out.println(Thread.currentThread().getName());
                    }
                    System.out.println(value);
                })
                .parallel()
                .peek(runnable -> running.set(false))
                .map(CompletableFuture::runAsync)// 10个runnable 映射为 异步的 CompletableFuture
                .forEach(CompletableFuture::join);// 10 个异步的 CompletableFuture的join
    }


    @NotNull
    private IntFunction<Runnable> getMapper(AtomicBoolean running) {
        return value -> (Runnable) () -> {
            while (running.get()) {
                sleepSecond(1);
                System.out.println(Thread.currentThread().getName());
            }
            System.out.println(value + " ");
        };
    }

    @ToString
    static class Machine {
        private final int id;
        private State state = State.ONE;

        Machine(int id) {
            this.id = id;
        }

        public enum State {
            ONE, TWO, THREE;

            State step() {
                if (equals(THREE)) {
                    return THREE;
                }
                return values()[ordinal() + 1];
            }
        }


        @SneakyThrows
        static Machine work(Machine machine) {
            if (!machine.state.equals(State.THREE)) {
                Thread.sleep(100);
                machine.state = machine.state.step();
            }
            System.out.println(machine);
            return machine;
        }


    }


    @Test
    public void test异步join_立即返回后台继续工作() {

        CompletableFuture.completedFuture(new Machine(0))
                .thenApplyAsync(Machine::work)
                .thenApplyAsync(Machine::work)
                .thenApplyAsync(Machine::work)
                .thenApplyAsync(Machine::work)
                .join();// 异步没有最后join,都不会发生
        CompletableFuture.runAsync(() -> System.out.println(1));

    }


    @Test
    public void test同步_完成工作后返回() {
        CompletableFuture.completedFuture(new Machine(0))
                .thenApply(Machine::work)
                .thenApply(Machine::work)
                .thenApply(Machine::work)
                .thenApply(Machine::work);// 同步好像加个join也没区别?
    }

    @Test
    public void dddd() {
        List<CompletableFuture<Integer>> collect = IntStream.range(1, 10)
                .mapToObj(i ->
                        CompletableFuture.supplyAsync(
                                        () -> {

                                            if (i == 5) {
                                                throw new NullPointerException();
                                            }
                                            sleepSecond(i);
                                            System.out.println(i);
                                            return i;
                                        }
                                )

                                .exceptionally(throwable -> {  //   如果这里不处理异常，那么异常会在所有任务完成后抛出
                                    sleepSecond(1);
                                    return 555;
                                })
                ).toList();
        CompletableFuture.allOf(collect.toArray(new CompletableFuture[]{})).join();

    }


    @Test
    public void test6() throws ExecutionException, InterruptedException {


        System.out.println(
                CompletableFuture.completedFuture(1).get()
        );
        CompletableFuture.completedFuture(2)
                .thenRunAsync(() -> System.out.println("thenRunAsync"))
                .get();
        CompletableFuture<Void> future = CompletableFuture.completedFuture(4)
                .thenAcceptAsync(integer -> System.out.println("" + integer));
        //

        future.get();

        /*

         *  2.then是future对象的方法 没有then是静态方法,是第一次,可以completedFuture完成的任务,也可以异步产生一个任务
         *  3.run方法执行 空空函数  accept  方法执行 消费函数  supply执行生产函数  apply 接受function
         *  Async结尾的函数异步执行,默认通过ForkJoinPool实现,也可以在第二个参数里指定线程池
         */


        CompletableFuture<Void> isStatic = CompletableFuture.runAsync(() -> System.out.println("runAsync is static"));


        CompletableFuture<Integer> future1 = CompletableFuture.completedFuture(5)
                .thenApplyAsync(integer -> integer + 42);

        CompletableFuture<Integer> future2 = CompletableFuture.completedFuture(5)
                .thenApplyAsync(integer -> integer + 2);

        Short join = future1.applyToEither(future2, Integer::shortValue)
                .join();

        Integer x = CompletableFuture.completedFuture(6)
                .thenComposeAsync(integer -> CompletableFuture.completedFuture(integer + 99))
                .get();   // compose 和apply类似 也是接受一个function ,但compose返回值必须是future


    }

    private void sleepSecond(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void furut() {
        List<CompletableFuture<Void>> collect = Stream.generate(() ->
                        CompletableFuture.runAsync(() -> {
                            sleepSecond(1);

                            System.out.println(Thread.currentThread().getName());
                        })
                )
                .limit(10)
                .toList();
        CompletableFuture.allOf(collect.toArray(new CompletableFuture[0]))
                .join();

        var futures = IntStream.range(1, 10)
                .mapToObj(i ->
                        CompletableFuture.supplyAsync(() -> {
                            sleepSecond(i);
                            System.out.println(i + " " + Thread.currentThread().getName());
                            return i;
                        })
                )
                .toList();
        Void all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}))
                .join();
        Object any = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[]{}))
                .join();
        System.out.println("any = " + any);
    }


}

