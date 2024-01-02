package 代码积累库.多线程与异步任务;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/9/12 15:43
 */
public class MyCompleteFuture {

    // join 和get 会阻塞当前线程等待 返回结果 其中get要处理异常,一般catch里也不会有其他逻辑,所以用join方便
    // getNow 不会阻塞当前线程 如果没有完成返回默认值


    // 初始化一个CompletableFuture  只有3种方式
    // runAsync 无返回结果的异步任务
    // supplyAsync 有返回结果的异步任务
    // CompletableFuture.completedFuture()  适合一个对象


    // 任务串行化方法 (都有对应的Async,后缀方法都是异步另外线程执行，没有就是复用之前任务的线程,默认forkjoin线程池(固定大小为cpu逻辑核心数-1),也可以指定线程池,但都是等待上一个执行完再执行)
    // thenRun 上面任务执行完执行
    // thenAccept-上面任务执行完执行+获取返回值+没有返回值
    // thenApply-上面任务执行完执行+获取返回值+有返回值
    // thenCompose- 上面任务执行完执行 接收返回值并生成新的任务  a接着b 类似 函数的compose 接着进行

    Integer join = CompletableFuture.completedFuture(1)
            .thenApply(integer -> integer + 1)
            // thenApply（）转换的是泛型中的类型，相当于将CompletableFuture 转换生成新的CompletableFuture
            .thenCompose(integer -> CompletableFuture.supplyAsync(() -> integer + 2))

            // thenCompose-接收返回值并生成新的任务  thenCompose（）用来连接两个CompletableFuture，是生成一个新的CompletableFuture。
            .join();


    // 对两个任务 处理
    // thenCombine = applyAfterBoth消费两个结果+返回结果  a 联合b 生成 c
    // thenAcceptBoth-消费两个结果+无返回
    // runAfterBoth-两个任务完成接着运行 不消费结果,也没有返回

    // applyToEither-两任务完成一个就执行+获取返回值+有返回值
    // acceptEither-两任务完成一个就执行+获取返回值+无返回值
    // runAfterEither-两任务完成一个就执行 一个runnable


    // 多任务组合
    // allOf-等待全部完成后才执行  始终返回一个null
    // anyOf-等待其中之一完成后就执行 返回最先完成的Future的返回值


    // 异常处理
    // handle-捕获结果或异常     并返回新结果
    // exceptionally 只能感知异常  并返回给定的默认值

    // whenComplete-感知结果或异常   无返回值 不能修改返回信息
    Integer join1 = CompletableFuture.completedFuture(1)
            .thenApply(integer -> integer + 1)
            // thenApply（）转换的是泛型中的类型，相当于将CompletableFuture 转换生成新的CompletableFuture
            .thenCompose(integer -> CompletableFuture.supplyAsync(() -> integer + 2))
            // thenCompose-接收返回值并生成新的任务  thenCompose（）用来连接两个CompletableFuture，是生成一个新的CompletableFuture。


            .thenCombine(CompletableFuture.supplyAsync(() -> 1), Integer::sum)



            .whenComplete((value, throwable) -> System.out.println(value))
            .exceptionally(throwable -> {
                System.out.println("error"+throwable.getMessage());
                return 0;
            })
            .join();

    @SneakyThrows
    private static void submitFuture() {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // execute 只能执行runnable  无返回值
        // submit 还能提交callable 返回future     时候future.get()才
        Future<Integer> future = threadPool.submit(() -> {
            System.out.println("submit call");
            int i = 1 / 0;
            return 1;
        });
        // System.out.println("future.isDone() = " + future.isDone());
        // Integer integer = future.get();
        System.out.println("future.isDone() = " + future.isDone());
        // System.out.println("integer = " + integer);


        Future<?> submit1 = threadPool.submit(() -> {
            System.out.println(1);
            int i = 1 / 0;

        });// runnable same
        threadPool.execute(() -> {
            int i = 1 / 0;
            System.out.println(11);
        });
    }

    public static void main(String[] args) {
        // submitFuture();
        Stream.generate(()->new Random().nextInt())
                .parallel().limit(100)
                .forEach(System.out::println);
        System.out.println(ForkJoinPool.commonPool().getPoolSize());
        System.out.println(Runtime.getRuntime().availableProcessors());


        int maxThread = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
        System.out.println("maxThread = " + maxThread);
    }
}
