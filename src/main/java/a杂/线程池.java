package a杂;

import lombok.SneakyThrows;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 13:15
 */
public class 线程池 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(0.3 == 0.1 * 3);
        //false
        String c = "𝄞";
        System.out.println();
        System.out.println(c.length());//2
        System.out.println(c.codePointCount(0, c.length()));//1

        /**
         * 提前准备  和重复利用
         * 控制最大并发数
         * 控制任务最大数  拒绝策略
         *
         * 复用线程  降低资源消耗
         * 提高响应速度
         * 可以管理线程数和任务数
         */

        AtomicInteger atomicInteger = new AtomicInteger(1);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                3,
                0,
                TimeUnit.MILLISECONDS, //救急队列的生存时间
                new ArrayBlockingQueue<>(2),  //阻塞队列
                new ThreadFactory() {  //线程工厂.方便创建线程起个好名字
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "myThread" + atomicInteger.getAndIncrement());
                    }
                },
                new ThreadPoolExecutor.AbortPolicy() //拒绝策略,默认终止并异常,还可以直接丢弃,丢弃最旧,抛给调用者线程
        );


        threadPool.submit(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
            }
        });


        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() {
                return "hello";
            }
        });
        new Thread(futureTask).start();
        System.out.println(futureTask.get());


        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();

    }


}

