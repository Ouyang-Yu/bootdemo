package 算法题;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/10/25 22:39
 */
public class condition实现顺序循环打印 {
    @Test
    public void lock() {
        // https://segmentfault.com/a/1190000021433079#item-1
        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();
        new Thread(print(lock, 0,a, b, "A")).start();
        new Thread(print(lock, 1,b, c, "B")).start();
        new Thread(print(lock, 2,c, a, "C")).start();
    }

    int state;

    Runnable print(Lock lock, int position, Condition cur, Condition next, String str) {
        return () -> {
            for (int i = 0; i < 100; i++) {
                lock.lock();
                try {//while 判断轮到当前position ,当前await 下一个signal
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
}
