package 代码积累库.多线程与异步任务;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/14 15:54
 */
public class deadLock {
static final Object a = new Object();
   static final Object b = new Object();

    @Test
    public void ds() {

    }

    @Test
    public static void deadLock() {

        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(" try a");
                synchronized (a) {// 只要有一个线程执行这个代码,a就被锁住了限制访问,其他线程无法访问a,也就不能执行这段代码
                    System.out.println(" get a");
                    Thread.sleep(1000);
                    System.out.println(" try b");
                    synchronized (b) {
                        System.out.println("get b");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(" try b");
                synchronized (b) {// 只要有一个线程执行这个代码,a就被锁住了限制访问,其他线程无法访问a,也就不能执行这段代码
                    System.out.println(" get b ");
                    Thread.sleep(1000);
                    System.out.println(" try a");
                    synchronized (a) {
                        System.out.println("get a");
                    }
                }
            }
        });

        t2.start();
        t1.start();
    }
    public static void main(String[] args) {
        deadLock();
    }
}
// LongAdder和DoubleAdder类：LongAdder和DoubleAdder类是对AtomicLong和AtomicDouble的改进。它们提供了更高的并发性能，在高并发场景下更适合使用。
// LongAdder和DoubleAdder类通过分解内部计数器，将更新操作分散到多个变量上，以减少竞争和锁争用。

