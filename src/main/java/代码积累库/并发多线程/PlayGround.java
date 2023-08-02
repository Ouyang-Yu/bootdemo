package 代码积累库.并发多线程;

import org.junit.jupiter.api.Test;

import java.util.concurrent.FutureTask;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/14 15:54
 */
public class PlayGround {
    @Test
    public void ds() {
        new Thread(new FutureTask<>(() -> "Hello")).start();
        // futureTask is also runnable

    }
}
