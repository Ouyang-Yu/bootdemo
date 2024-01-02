package 代码积累库.多线程与异步任务;

/**
 * @author OuYang  <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/19 18:44
 */

import lombok.SneakyThrows;

import java.util.concurrent.RecursiveTask;

/**
 * 计算 0-10000 阶乘
 */
public class MyForkJoinTask extends RecursiveTask<Long> {
    /**
     * 起始值
     */
    private final long start;
    /**
     * 结束值
     */
    private final long end;

    public MyForkJoinTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 计算逻辑
     * 进行任务的拆分 以及 达到阈值的计算
     *
     * @return
     */
    @Override
    protected Long compute() {
        // 校验是否达到了阈值
        /**
         * 阈值
         */
        long threshold = 10L;
        if (end - start <= threshold) {
            // 处理并返回结果
            return handle();
        } else {
            // 没有达到阈值 计算一个中间值
            long mid = (start + end) / 2;
            // 拆分 左边的
            MyForkJoinTask left = new MyForkJoinTask(start, mid);
            // 拆分右边的
            MyForkJoinTask right = new MyForkJoinTask(mid + 1, end);
            // 添加到任务列表
            invokeAll(left, right);
            // 合并结果并返回
            return left.join() + right.join();
        }
    }

    /**
     * 处理的任务
     *
     * @return
     */
    @SneakyThrows
    public Long handle() {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
            Thread.sleep(1);
        }
        return sum;
    }

}

