package a综合;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.hutool.core.thread.ThreadUtil.sleep;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 13:15
 */
public class 线程池 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        /**
         * 提前准备  和重复利用
         *
         * 复用线程  降低资源消耗
         * 提高响应速度
         * 可以管理最大线程数和阻塞队列的任务数
         */

        AtomicInteger atomicInteger = new AtomicInteger(1);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1, 3, 0, TimeUnit.MILLISECONDS, //救急队列的生存时间
                new ArrayBlockingQueue<>(2),  //阻塞队列
                new ThreadFactory() {  //线程工厂.方便创建线程起个好名字
                    @Override
                    public Thread newThread(@NotNull Runnable r) {
                        return new Thread(r, "myThread" + atomicInteger.getAndIncrement());
                    }
                }, new ThreadPoolExecutor.AbortPolicy() //拒绝策略,默认终止并异常,还可以直接丢弃,丢弃最旧,抛给调用者线程
        );


        /**
         * Callable 用于产生结果，Future 用于获取结果
         * futureTask自己就能获取结果,不用由线程池返回一个future再get
         */
        Future<String> future = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "haha";
            }
        });
        String s = future.get();


        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        threadPool.submit(futureTask);
        futureTask.get();


        /**
         * Future 类通过 get() 方法阻塞等待获取异步执行的运行结果，性能比较差。
         *
         * JDK1.8 中，Java 提供了 CompletableFuture 类，它是基于异步函数式编程。
         * 相对阻塞式等待返回结果，CompletableFuture 可以通过回调的方式来处理计算结果，实现了异步非阻塞，性能更优。
         * 异步任务结束时，会自动回调某个对象的方法
         * 异步任务出错时，会自动回调某个对象的方法
         * 主线程设置好回调后，不再关心异步任务的执行
         *
         *
         *1、 两种创建创建方式   runAsync 和supplyAsync
         *
         *
         *
         *2.    四种获取结果的方式
         * public T get()
         * public T get(long timeout, TimeUnit unit)
         * public T getNow(T valueIfAbsent)
         * public T join()
         *
         * 「get()和get(long timeout, TimeUnit unit)」 => 在Future中就已经提供了，后者提供超时处理，如果在指定时间内未获取结果将抛出超时异常
         * 「getNow」 => 立即获取结果不阻塞，结果计算已完成将返回结果或计算过程中的异常，如果未计算完成将返回设定的valueIfAbsent值
         * 「join」 => 方法里不会抛出异常
         *
         *
         *
         * 3.任务编排
         * thenRun 和thenRunAsync  做完第一个任务后，再做第二个任务 ,不依赖上一个任务的结果,也没有返回值,Async表示与第一个任务不公用一个线程池   ( 调用thenRun方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池 调用thenRunAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。)
         * thenAccept/thenAcceptAsync  做完第一个任务后，再做第二个任务,依赖上一个任务结果,没有返回值
         * thenApply/thenApplyAsync    做完第一个任务后，再做第二个任务,依赖上一个任务结果,有返回值
         *
         * 4.多任务组合
         * thenCombine / thenAcceptBoth / runAfterBoth都表示：「当任务一和任务二都完成再执行任务三」。
         * 「runAfterBoth」 不会把执行结果当做方法入参，且没有返回值
         * 「thenAcceptBoth」: 会将两个任务的执行结果作为方法入参，传递到指定方法中，且无返回值
         * 「thenCombine」：会将两个任务的执行结果作为方法入参，传递到指定方法中，且有返回值
         *
         * applyToEither / acceptEither / runAfterEither 都表示：「两个任务，只要有一个任务完成，就执行任务三」。
         * 「runAfterEither」：不会把执行结果当做方法入参，且没有返回值
         * 「acceptEither」: 会将已经执行完成的任务，作为方法入参，传递到指定方法中，且无返回值
         * 「applyToEither」：会将已经执行完成的任务，作为方法入参，传递到指定方法中，且有返回值
         *
         * 5.异常回调
         * whenComplete + exceptionally
         *
         *
         *
         *
         */
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1:洗水壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T1:烧开水...");
            sleep(15, TimeUnit.SECONDS);
        });

//任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2:洗茶壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T2:洗茶杯...");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2:拿茶叶...");
            sleep(1, TimeUnit.SECONDS);
            return "龙井";
        });

//任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 = f1.thenCombine(
                f2,
                (__, tf) -> {
                    System.out.println("T1:拿到茶叶:" + tf);
                    System.out.println("T1:泡茶...");
                    return "上茶:" + tf;
                }
        );

//等待任务3执行结果
        System.out.println(f3.join());

        /**
         * spring 注解 异步
         * 1.配置一个线程池bean
         * 2.@EnableAsync
         * 3.在需要被异步调用的方法外加上 @Async("beanName")
         */
    }
}

