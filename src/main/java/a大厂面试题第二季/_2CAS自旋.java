package a大厂面试题第二季;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-18 22:57
 */
public class _2CAS自旋 {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    @SneakyThrows
    public void lock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println("try to set");
            Thread.sleep(500);
        }
    }
    public void unLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }
    public static void main(String[] args) throws InterruptedException {
        _2CAS自旋 casSpin = new _2CAS自旋();
        new Thread(new Runnable() {
            @Override
            public void run() {
                casSpin.lock();
                try{TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}

                casSpin.unLock();
            }
        },"aa").start();

        Thread.sleep(1000); //保证先定义的线程 先执行
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                casSpin.lock();   //由于线程aa暂停3s才释放,变量不为空 所以bb一直在compare 自旋
                //不会立即阻塞,而是循环的方式获取锁
                //避免线程切换的上下文开销,但是会消耗CPU
                Thread.sleep(1000);
                casSpin.unLock();
            }
        },"bb").start();

    }
}
