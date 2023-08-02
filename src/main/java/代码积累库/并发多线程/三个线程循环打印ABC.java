package 代码积累库.并发多线程;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/3 11:20
 */
public class 三个线程循环打印ABC {
    // 共享计数器
    private static int sharedCounter = 0;

    public static void main(String[] args) {
        // 打印的内容
        String printString = "ABC";
        // 定义循环栅栏
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        // 执行任务
        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < printString.length(); i++) {
                    synchronized (this) {
                        sharedCounter = sharedCounter > 2 ? 0 : sharedCounter; // 循环打印
                        System.out.println(printString.toCharArray()[sharedCounter++]);
                    }


                    cyclicBarrier.await(); // 阻塞所有线程直到这句话执行三次才放行

                }
            }
        };
        // 开启多个线程
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

}
