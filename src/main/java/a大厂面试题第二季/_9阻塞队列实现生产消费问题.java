package a大厂面试题第二季;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 22:37
 */
@Slf4j
class MyData9 {
    volatile boolean open = true;
    AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue ;

    public MyData9(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @SneakyThrows
    void produce() {
        String data ;
        boolean success;
        while (open) {
            data = atomicInteger.incrementAndGet() + "";
            success = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (success) {
                log.info("{} produce",Thread.currentThread().getName());
            } else {
                log.info("a");
            }

            Thread.sleep(1000);
        }
        //open=false 情况下
        log.info(Thread.currentThread().getName()+" stop produce");
    }

    @SneakyThrows
    void consume() {
        String result;
        while (open) {
             result = blockingQueue.poll(2L, TimeUnit.SECONDS);


            if (result != null && !"".equals(result)) {
                log.info("{}      consumed", Thread.currentThread().getName());
            } else { // 2s后停止阻塞, 没取到结果
                open = false;// 关闭整个生产消费过程
                log.info("{} consume failed",Thread.currentThread().getName());
                return;
            }
        }
    }
}
public class _9阻塞队列实现生产消费问题 {
    @SneakyThrows
    public static void main(String[] args) {
//        MyData9 data = new MyData9(new ArrayBlockingQueue<String>(10));
        MyData9 data = new MyData9(new SynchronousQueue<>());
        new Thread(() -> {
            data.produce();
        }, "a").start();

        new Thread(() -> {
            data.consume();
        }, "b").start();
//
//        Thread.sleep(8);  // 8s 后停止过程
//        data.open = false;
    }
}
