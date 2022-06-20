package a大厂面试题第二季;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 11:51
 */
@Slf4j
class MyCache {
    volatile Map<String, String> map = new HashMap<String, String>(); //保证线程可见性

    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    void put(String key, String value) {
        readWriteLock.writeLock().lock();
        log.info("{} is writing", Thread.currentThread().getName());
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        map.put(key, value);
        log.info("{}  write done", Thread.currentThread().getName());
        readWriteLock.writeLock().unlock();
    }

    void get(String s) {
        readWriteLock.readLock().lock();
        log.info("{} is reading", Thread.currentThread().getName());
        try{TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

        log.info("{} read done ------->{}", Thread.currentThread().getName(), map.get(s));

        readWriteLock.readLock().unlock();
    }
}

@Slf4j
public class _3读写锁 {

    public static void main(String[] args) {

        MyCache cache = new MyCache(); //线程操作资源类

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                cache.put(finalI + "", finalI + "");
            }, i + ""
            ).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI1 = i;
            new Thread(() -> {
                cache.get(finalI1 + "");
            }, i + "").start();
        }

    }
}
