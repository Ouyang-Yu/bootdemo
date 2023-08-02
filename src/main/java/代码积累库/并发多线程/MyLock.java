package 代码积累库.并发多线程;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/15 14:14
 */
public class MyLock {
    @Test
    public void unlock() {
        ReentrantLock reentrantLock = new ReentrantLock();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        reentrantReadWriteLock.writeLock().lock();// lock必须在try外面,因为lock()也有可能失败
        // 必须跟try之间不能有代码,因为如果之间代码发生异常,将走不到 unlock
        try {

        } finally {
            reentrantReadWriteLock.writeLock().unlock();// unlock必须在finally第一行,因为如果有代码异常将走不到unlock
        }


    }
}
