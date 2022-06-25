package a大厂面试题第二季.生产消费问题;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-20 19:07
 */

@Slf4j
public class 生产消费者之阻塞队列线程内实现 {

    volatile  static  boolean open = true;

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(100);  //1.阻塞队列
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (open) {                                                               //2.1 循环
                    // log.info("{}  produce", Thread.currentThread().getName());      //2.3log
                    blockingQueue.offer(new Random().nextInt(100));                                                //  2.2put

                    //Thread.sleep(1000);

                }
            }
        }, "p").start();


        new Thread(() -> {
            while (open) {

                    Integer result = blockingQueue.poll();//如果没有数据,就会阻塞在这里
                   // log.info("{}  consume  {}", Thread.currentThread().getName(), result);
                   // Thread.sleep(1000);//必须要有 // ,而且不大于 produce暂停时间 ,否则吃的速度跟不上生产速度 ,可以快,因为没有东西消费就会阻塞
            }
        }, "c").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        open = false;
    }
}



