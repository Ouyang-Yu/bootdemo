package a大厂面试题第二季.生产消费问题;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-24  21:49
 */
public class 面试版实现 {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        ReentrantLock reentrantLock = new ReentrantLock(true);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                   reentrantLock.lock();
                   try {
                       blockingQueue.put("a");  //1.put
                       System.out.println("p");    //2.打印
                       Thread.sleep(1000);    //3.休眠
                   } finally {
                     reentrantLock.unlock();
                   }
                }
            }
        }, "producer").start();

        new Thread(new Runnable()  {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                  reentrantLock.lock();
                  try {
                      blockingQueue.take();
                      System.out.println("c");
                      Thread.sleep(1000);
                  } finally {
                    reentrantLock.unlock();
                  }
                }
            }
        }, "consumer").start();

    }
}
