package a大厂面试题第二季.生产消费问题;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-19 21:09
 */
@Slf4j
class MyData {
    int number = 0;
    Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    @SneakyThrows
    public void increase()  {
        lock.lock();
        while (number != 0) { //不能用if,存在虚假唤醒问题
            condition.await();  //不为0 就这在等着            直到为0才能 往下走
        }
        number++;
        log.info("{}生产,剩余--{}",Thread.currentThread().getName(),number);
        condition.signalAll();   //通知唤醒
        lock.unlock();
    }
    @SneakyThrows
    public void decrease() {
        lock.lock();
        while (number == 0) { //不能用if,存在虚假唤醒问题
            condition.await();  //  为0 就这在等着            直到不为0才能 往下走
        }
        number--;
        log.info("{}消费,剩余--{}",Thread.currentThread().getName(),number);
        condition.signalAll();   //通知唤醒

        lock.unlock();
    }


}
/**
 * aa线程生产五次  但是必须等bb线程消费一个后才能继续生产下一个
 */
public class _7生产消费问题 {

    public static void main(String[] args) {
        MyData data = new MyData();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                data.increase();
            }
        }, "aa").start();


        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                data.decrease();
            }
        }, "bb").start();

    }

}
