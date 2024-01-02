package 代码积累库.springmvc.retry;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/9/1 10:01
 */
@EnableRetry//开启重试功能：在启动类或者配置类上添加 @EnableRetry 注解
@Configuration

public class MySpringRetry {
    /**
     * 由于 Spring Retry 用到了 Aspect 增强，所以就会有使用 Aspect 不可避免的坑——方法内部调用 不能在本类使用
     *
     * @Recover 注解标记的方法必须和被 @Retryable 标记的方法在同一个类中

     *  recover() 方法参数类型  重试方法抛出的异常类型需要与保持一致
     * recover() 方法返回值需要与重试方法返回值保证一致
     * recover() 方法中不能再抛出 Exception，否则会报无法识别该异常的错误
     */


    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 2),value = {NullPointerException.class} )
    //最大重试次数为 3，第一次重试间隔为 2s，之后以 2 倍大小进行递增，第二次重试间隔为 4 s，第三次为 8s
    // 只对特定类型的异常进行重试。默认：所有异常
    public void chu() {
        int a = 1 / 0;
    }

    @Recover
    public void recover(ArithmeticException exception) {
        System.out.println("max retry and do something");
    }
}
