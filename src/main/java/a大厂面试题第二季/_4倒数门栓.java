package a大厂面试题第二季;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 12:40
 */
@Slf4j
public class _4倒数门栓 {
    public static void main(String[] args) throws InterruptedException {
        int num = 5;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                log.info("{} leave room",Thread.currentThread().getName());
                countDownLatch.countDown();
            }, i+"").start();
        }
        countDownLatch.await(); //直到 计数器 归零才能往下走
        log.info("close door");

    }
}
