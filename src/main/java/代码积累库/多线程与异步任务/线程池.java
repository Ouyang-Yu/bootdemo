package 代码积累库.多线程与异步任务;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 13:15
 */
public class 线程池 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        AtomicInteger atomicInteger = new AtomicInteger(1);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() + 1,
                3,
                0,// 非核心线程的空闲生存时间
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2),  // 阻塞队列
                r -> new Thread(r, "myThread" + atomicInteger.getAndIncrement()),//线程工厂
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略,默认终止并异常,还可以直接丢弃,丢弃最旧,抛给调用者线程
        );


        /**
         * Callable 用于产生结果，Future 用于获取结果
         * futureTask自己就能获取结果,不用由线程池返回一个future再get
         */
        Future<String> future = threadPool.submit(() -> "haha");
        String s = future.get();


        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1);
        threadPool.submit(futureTask);
        futureTask.get();


        //execute 接受 runnable
        // submit 还可以接受callable 返回一个future  通过get获取结果,如果有异常也会在get时候抛出

    }
}

