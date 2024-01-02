package 代码积累库.多线程与异步任务;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/3 11:20
 */
public class 三个线程循环打印ABCTest {

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
    static int state;

    @Test
    public void lock() {

        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();
        new Thread(print(lock, 0,a, b, "A")).start();
        new Thread(print(lock, 1,b, c, "B")).start();
        new Thread(print(lock, 2,c, a, "C")).start();
    }


    Runnable print(Lock lock, int position, Condition cur, Condition next, String str) {
        return () -> {
            for (int i = 0; i < 100; i++) {
                lock.lock();
                try {
                    while (state % 3 != position) {
                        cur.await();  //如果不是轮到当前线程就阻塞
                    }
                    System.out.println(Thread.currentThread().getName()+str);
                    state++;
                    next.signal();//唤醒下一个线程
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        };
    }
    
    @Test
    public void atomicAndCAS() {
        AtomicInteger state = new AtomicInteger(0);

        new Thread(()->print(state,0,"A")).start();
        new Thread(()->print(state,1,"B")).start();
        new Thread(()->print(state,2,"C")).start();
    }

    private void print(AtomicInteger state, int position, String str) {
        for (int i = 0; i < 100; ) {
            if (state.get() % 3 == position) {
                System.out.println(Thread.currentThread().getName() + str);
                state.compareAndSet(state.get(), state.get() + 1);
                i++;
            }
        }
    }



}
