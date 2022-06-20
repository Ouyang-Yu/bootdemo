package a大厂面试题第二季;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 21:41
 */
@Slf4j
class MyData1 {
    int flag = 1;
    ReentrantLock reentrantLock = new ReentrantLock();
    Condition c1 = reentrantLock.newCondition();
    Condition c2 = reentrantLock.newCondition();
    Condition c3 = reentrantLock.newCondition();


    @SneakyThrows
    void printA() {
        reentrantLock.lock();
        try {
            while (flag != 1) {
                c1.await(); //
            }
            log.info("{}", Thread.currentThread().getName());
            flag = 2;
            c2.signal();

            Thread.sleep(500);
        } finally {
            reentrantLock.unlock();
        }
    }

    @SneakyThrows
    void printB() {
        reentrantLock.lock();
        try {
            while (flag != 2) {
                c2.await(); //
            }
            log.info("{}", Thread.currentThread().getName());
            flag = 3;
            c3.signal();

            Thread.sleep(500);

        } finally {
            reentrantLock.unlock();
        }
    }

    @SneakyThrows
    void printC() {
        reentrantLock.lock();
        try {
            while (flag != 3) {
                c3.await(); //
            }
            log.info("{}", Thread.currentThread().getName());
            flag = 1;
            c1.signal();

            Thread.sleep(500);
        } finally {
            reentrantLock.unlock();
        }
    }
}

public class _8多个线程顺序循环执行 {

    public static void main(String[] args) {
        MyData1 myData1 = new MyData1();
        new Thread(() -> {
            while (true) {
                myData1.printA();
            }
        }, "a").start();

        new Thread(() -> {
            while (true) {
                myData1.printB();
            }
        }, "b").start();

        new Thread(() -> {
            while (true) {
                myData1.printC();
            }
        }, "c").start();


    }
}
