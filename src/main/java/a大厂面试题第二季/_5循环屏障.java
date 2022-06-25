package a大厂面试题第二季;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 13:00
 */
@Slf4j
public class _5循环屏障 {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> log.info(" 召唤神龙"));




        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                int numberWaiting = cyclicBarrier.getNumberWaiting();
                log.info("收集到第{}龙珠,还差{}个", Thread.currentThread().getName(), 7 -1- numberWaiting);
                //log 不能再await之后 不然会在线程里阻塞住,直到barrier解开,七个线程同时继续执行
                try {
                    cyclicBarrier.await();   //
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, i+1+"").start();

            Thread.sleep(1000);


        }
    }
}
