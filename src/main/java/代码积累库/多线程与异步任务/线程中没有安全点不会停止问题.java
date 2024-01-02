package 代码积累库.多线程与异步任务;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/19 17:04
 */
public class 线程中没有安全点不会停止问题 {
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 100000000; i++) {
                num.getAndAdd(1);
                // if (i % 1000 == 0) {
                //     try {
                //         Thread.sleep(0);  //set safe point
                //     } catch (InterruptedException e) {
                //         throw new RuntimeException(e);
                //     }
                // }
            }
            System.out.println(Thread.currentThread().getName() + "执行结束!");
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        Thread.sleep(1000);
        System.out.println("num = " + num);
    }

    /*
        按理来说主线程会直接打印num
        但是却会等待两个子线程结束再打印



        原因是int范围内的可数循环会在结束之后才会进safe point
        没有安全点,当这个循环运行时，JVM将无法停止线程。所以主线程无法继续运行


        long范围内的的不可数循坏中间就会有safe point ,就可以暂停子线程进入主线程打印num了

        如何在可数循环内手动设置安全点呢?
        调用native方法就可以手动进入安全点了,比如Thread.sleep(0)
        但是getAndAdd也是native方法,为什么不会进安全点呢,因为循环内热点代码的安全点被JIT优化了


     */

    /**
     * safe point
     * 安全点位置的选取基本上是以“是否具有让程序长时间执行的特征”为标准进行选定的。
     * HotSpot会在所有方法的临返回之前，以及所有非counted loop的循环的回跳之前放置安全点。
     * “长时间执行”的最明显特征就是指令序列的复用，例如方法调用、循环跳转、异常跳转等都属于指令序列复用，所以只有具有这些功能的指令才会产生安全点。
     *
     *   没有到安全点，是不能 STW，从而进行 GC 的
     *   线程不会进入安全点，GC 线程就得等着当前的线程循环结束，进入安全点，才能开始工作。
     */
}
