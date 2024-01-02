package 代码积累库.springmvc.retry;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/9/1 12:45
 */
public class GuavaRetry {
//<dependency>
//     <groupId>com.github.rholder</groupId>
//     <artifactId>guava-retrying</artifactId>
//     <version>2.0.0</version>
// </dependency>

    public String guavaRetry(Integer num) {
        // Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
        //         //无论出现什么异常，都进行重试
        //         .retryIfException()
        //         //返回结果为 error时，进行重试
        //         .retryIfResult(result -> Objects.equals(result, "error"))
        //         //重试等待策略：等待 2s 后再进行重试
        //         .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS))
        //         //重试停止策略：重试达到 3 次
        //         .withStopStrategy(StopStrategies.stopAfterAttempt(3))
        //         .withRetryListener(new RetryListener() {
        //             @Override
        //             public <V> void onRetry(Attempt<V> attempt) {
        //                 System.out.println("RetryListener: 第" + attempt.getAttemptNumber() + "次调用");
        //             }
        //         })
        //         .build();
        // try {
        //     retryer.call(() -> testGuavaRetry(num));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        return "test";
    }

}
