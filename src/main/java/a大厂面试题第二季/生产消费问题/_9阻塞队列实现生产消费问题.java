package a大厂面试题第二季.生产消费问题;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 22:37
 */
@Slf4j
class MyData9 {
    volatile boolean open = true;
    AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue ;
    ReentrantLock lock=new ReentrantLock();

    public MyData9(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @SneakyThrows
    void produce() {
        String data ;
        boolean success;
        while (open) {
            lock.lock();   //保证 打完日志后 才能另外线程消费  否则可能出现 消费日志先于生产日志问题
            data = atomicInteger.incrementAndGet() + "";
            success = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (success) {
                log.info("{} produce{}",Thread.currentThread().getName(),data);
            } else {
                log.info("a");
            }
            lock.unlock();

            Thread.sleep(1000);
        }
        //open=false 情况下
        log.info(Thread.currentThread().getName()+" stop produce");
    }

    @SneakyThrows
    void consume() {
        String result;
        while (open) {
            lock.lock();

            result = blockingQueue.poll(2L, TimeUnit.SECONDS);

            if (result != null && !"".equals(result)) {
                log.info("{}      consumed{}", Thread.currentThread().getName(),result);
            } else { // 2s后停止阻塞, 没取到结果
                open = false;// 关闭整个生产消费过程
                log.info("{} consume failed",Thread.currentThread().getName());
                return;
            }
            lock.unlock();
        }
    }
}
public class _9阻塞队列实现生产消费问题 {
    @SneakyThrows
    public static void main(String[] args) {
        MyData9 data = new MyData9(new ArrayBlockingQueue<String>(10));
        new Thread(data::produce, "a").start();

        new Thread(data::consume, "b").start();

//        Thread.sleep(8);  // 8s 后停止过程
//        data.open = false;
    }
}
