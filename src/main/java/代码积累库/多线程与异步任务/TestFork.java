package 代码积累库.多线程与异步任务;

import lombok.SneakyThrows;

import java.util.concurrent.ForkJoinPool;

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @datetime 2023/5/22 16:57
 */
public class TestFork {
    public static void forkJoinInvok(long start, long end) {
        long sum = 0;
        long currentTime = System.currentTimeMillis();
        // 创建初始化任务
        MyForkJoinTask submitTask = new MyForkJoinTask(start, end);
        // 将初始任务扔进连接池中执行
        new ForkJoinPool().invoke(submitTask);

        // forkJoinPool.submit(submitTask);
        // System.out.println("异步方式，任务结束才会调用该方法,当前耗时"+(System.currentTimeMillis() - currentTime));
        // 等待返回结果
        sum = submitTask.join();
        // forkjoin调用方式耗时
        System.out.println("forkJoin调用：result:" + sum);
        System.out.println("forkJoin调用耗时：" + (System.currentTimeMillis() - currentTime));
    }

    /**
     * 普通方式调用
     *
     * @param start
     * @param end
     */
    @SneakyThrows
    public static void normalInvok(long start, long end) {
        long sum = 0;
        long currentTime = System.currentTimeMillis();
        for (long i = start; i <= end; i++) {
            sum += i;

            Thread.sleep(1);

        }
        // 普通调动方式耗时
        System.out.println("普通调用：result:" + sum);
        System.out.println("普通调用耗时：" + (System.currentTimeMillis() - currentTime));
    }

    public static void main(String[] args) {
        // 起始值的大小
        long start = 0;
        // 结束值的大小
        long end = 10000;
        // forkJoin 调用
        forkJoinInvok(start, end);
        System.out.println("========================");
        // 普通调用
        normalInvok(start, end);
    }
}
