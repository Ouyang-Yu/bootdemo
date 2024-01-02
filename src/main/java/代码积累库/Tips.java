package 代码积累库;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.Collectors;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-09 20:06
 */

@Slf4j
@Data
public class Tips {
    @Test
    public void asd() {
        new HashMap<String,String>(){{
            put("1", "1");
            put(null, "1");
        }}.forEach((key,value)-> System.out.println(key+" = "+value));

        String s = "aAbbccc"; //
        s = s.toLowerCase();
        Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(
                        ss -> ss,// 通过自身分组,也就是map的key
                        Collectors.counting()// 分组之后    做什么返回什么?每组个数!就是value
                ))
                .forEach((key, value) -> System.out.println(key + " " + value));

        Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(a -> a))// group一个参数,仅仅按key分组,value就是每组
                .forEach((key, value) -> System.out.println(key + " " + value));
        Arrays.stream(s.split(""))
                .collect(Collectors.toMap(
                        a->a,
                        String::length,
                        Integer::sum
                )).forEach((key,value)-> System.out.println(key+" = "+value));
    }

    @Test
    public void d() {
        new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
            {//通过子类实现
                run();

            }
        };
    }
    @Test
    public void ruoyi() {
        //        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //        PageHelper.startPage(
        //                Convert.toInt(request.getParameter("pageNum"), 1),
        //                Convert.toInt(request.getParameter("pageSize"), 10),
        //                SqlUtil.escapeOrderBySql(request.getParameter("orderByColumn"))//非空和sql正则
        //        ).setReasonable(Convert.toBool(request.getParameter("reasonable")));
    }


    /**
     * 1.集合初始化的工厂方法<br>
     * 2.stream 新增  takeWhile()  dropWhile()
     * 3.optional 新增ofNullable ifPresentOrElse
     * 4.ProcessHandle
     * 其他
     * 模块化  string改为byte[]实现  G1变为默认垃圾收集器   提前编译  新增SHA-3
     */
    @Test
    public static void jdk9() {
        Optional.ofNullable(null).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("null")
        );

        ProcessHandle.Info info = ProcessHandle.current().info();
        System.out.println(info);
        //[user: Optional[OUYANG\ouyan],
        // cmd: C:\Users\ouyan\.jdks\graalvm-ce-17\bin\java.exe,
        // startTime: Optional[2023-04-02T11:48:26.146Z],
        // totalTime: Optional[PT0.1875S]]


    }

    /**
     * https://mp.weixin.qq.com/s/KrPnLMPrs9NO-iwPEMhZpw
     * 延迟队列允许我们根据元素的延迟时间对其进行排序
     * 它实现了 BlockingQueue 接口。只有当元素的时间过期时，才能从队列中取出。
     */
    @Test
    public void 延迟队列() throws InterruptedException {
        record DelayEvent(long expiredTime, String msg) implements Delayed {
            @Override
            public int compareTo(@NotNull Delayed o) {
                return (int) (this.expiredTime - ((DelayEvent) o).expiredTime);
            }

            @Override
            public long getDelay(@NotNull TimeUnit unit) {
                return unit.convert(
                        expiredTime - System.currentTimeMillis(),
                        TimeUnit.MILLISECONDS
                );
            }

        }
        DelayQueue<DelayEvent> delayQueue = new DelayQueue<>();
        delayQueue.offer(new DelayEvent(System.currentTimeMillis() + 2000, "1"));

        log.info(delayQueue.take().msg());
    }


    /**
     * 在很多场景下，它能让我们实现无锁的算法。当多个线程更新一个共同的值的时候，它通常会比 AtomicLong 更合适。
     */
    @Test
    public void 并发累加器() throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(Long::sum, 10);

        Runnable runnable = () -> accumulator.accumulate(1);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            executor.submit(runnable);
        }
        executor.shutdown();

        if (executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
            System.out.println(accumulator.get());
        }
        assert accumulator.get() == 60;
    }
    @Test
    public void das() {

    }

    @Test
    public void 字节数组与十六进制() {
        String hex = HexFormat.of().formatHex(new byte[]{127, 0, -50, 105});
        log.info(hex);
        System.out.println(Arrays.toString(HexFormat.of().parseHex(hex)));

        //int 和任意进制的字符串 转换
        int x = Integer.parseInt(hex, 16);
        System.out.println(x);
        assert hex.equals(Integer.toString(x, 16));

    }

    @Test
    public void 数组二分查找() {
        int[] t = new int[]{1, 2, 4, 5};
        int x = Arrays.binarySearch(t, 3);
        //返回    -(insertion point)-1
        System.out.println(x); //-3
        assert ~x == 2; //“按位非”运算符  按位取反
        System.out.println(~2);

    }

    @Test
    public void phaser() {
        Phaser phaser = new Phaser(50);


        Runnable r = () -> {
            System.out.println("phase-0");
            phaser.arriveAndAwaitAdvance(); //不管线程到这 都必须等待其他线程 到才能走
            System.out.println("phase-1");
            phaser.arriveAndAwaitAdvance();
            System.out.println("phase-2");
            phaser.arriveAndDeregister(); //到了就注销线程
        };

        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executor.submit(r); //开辟50个线程执行r
        }


    }

    /**
     * 如果我们需要对 bit 数组进行一些操作该怎么办呢？你是不是会使用 boolean[] 来实现呢？
     * 其实，有一种更有效、更节省内存的方法来实现。这就是 BitSet 类。BitSet 类允许我们存储和操作 bit 的数组。
     * 与 boolean[] 相比，它消耗的内存要少 8 倍。我们可以对数组进行逻辑操作，例如：and、or、xor。
     */
    @Test
    public void bitSet() {
        BitSet bitSet1 = BitSet.valueOf(new byte[]{0, 2, 4});
        System.out.println(bitSet1);

        bitSet1.xor(new BitSet() {{
            set(1);
            set(2);
            set(3);
        }});

        System.out.println(bitSet1);

    }

}
