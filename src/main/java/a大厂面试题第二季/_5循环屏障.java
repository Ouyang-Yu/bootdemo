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
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> log.info(" 召唤神龙"));
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                log.info("收集到龙珠:{}",Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();   //
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, i+"").start();

        }
    }
}
