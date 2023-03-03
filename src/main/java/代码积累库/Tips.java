//package 代码积累库;
//
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.LongAccumulator;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * @author ouyangyu369@gmail.com
// * @time 2022-05-09 20:06
// */
//
//@Slf4j
//public class Tips {
//
//    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
//        /*
//        工厂方法出来的集合 是不可变的
//         */
//        List<String> a = Arrays.asList("da", "sd"); //不可变,添加元素会报错
//        List<Integer> integers = List.of(1, 3);
//        Map<String, Integer> map = Map.of("a", 1);
//        //both immutable
//
//        List<Integer> collect = Stream.of(1, 20).collect(Collectors.toList()); //可变
//        collect.add(2);
//
//
//        FutureTask<Integer> futureTask = new FutureTask<>(() -> new Random().nextInt());
//        new Thread(futureTask, "t3").start();
//        log.debug(String.valueOf(futureTask.get()));
//
//        /*
//        直接写的小数 都是double  其他要指定
//         3.4F
//         */
//        float aa = 3.4F;
//
//

//
//
//
//
//
//        /*
//         * 多个线程执行 一个FutureTask的时候，只会计算一次
//         *
//         * FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
//         *
//         * // 开启两个线程计算futureTask
//         * new Thread(futureTask, "AAA").start();
//         * new Thread(futureTask, "BBB").start();
//         * 如果我们要两个线程同时计算任务的话，那么需要这样写，需要定义两个futureTask
//         *
//         * FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
//         * FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread2());
//         *
//         * // 开启两个线程计算futureTask
//         * new Thread(futureTask, "AAA").start();
//         *
//         * new Thread(futureTask2, "BBB").start()
//         */
//
//
//        Map<String, Long> longMap = Stream.of("abc".split(""))
//                .collect(Collectors.groupingBy(
//                        String::toString, //等同于  s->s
//                        Collectors.counting()
//                ));
//        longMap.forEach((k, v) -> System.out.println(k + ":" + v));
//
//
//        Stream<String> stream = Stream.of(new String[10]);//从数组中获取stream
//        stream.forEach(System.out::println);
//
//
//    }
//
//    @Test
//    private void data转化() {
//        LocalDateTime now = LocalDateTime.now();
//        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
//        LocalDateTime dateTime = nowDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//    }
//
//    /**
//     * 1.集合初始化的工厂方法<br>
//     * 2.stream 新增  takeWhile()  dropWhile()
//     * 3.optional 新增ofNullable ifPresentOrElse
//     * 4.ProcessHandle
//     * 其他
//     * 模块化  string改为byte[]实现  G1变为默认垃圾收集器   提前编译  新增SHA-3
//     */
//    @Test
//    public void jdk9() {
//        Optional.ofNullable(null).ifPresentOrElse(
//                System.out::println,
//                () -> System.out.println("null")
//        );
//
//        System.out.println(ProcessHandle.current().info().toString());
//    }
//
////    /**
////     * https://mp.weixin.qq.com/s/KrPnLMPrs9NO-iwPEMhZpw
////     * 延迟队列允许我们根据元素的延迟时间对其进行排序
////     * 它实现了 BlockingQueue 接口。只有当元素的时间过期时，才能从队列中取出。
////     */
////    @Test
////    public void 延迟队列() throws InterruptedException {
////        record DelayEvent(long expiredTime, String msg) implements Delayed {
////            @Override
////            public int compareTo(@NotNull Delayed o) {
////                return (int) (this.expiredTime - ((DelayEvent) o).expiredTime);
////            }
////
////            @Override
////            public long getDelay(@NotNull TimeUnit unit) {
////                return unit.convert(
////                        expiredTime - System.currentTimeMillis(),
////                        TimeUnit.MILLISECONDS
////                );
////            }
////        }
////        DelayQueue<DelayEvent> delayQueue = new DelayQueue<>();
////        delayQueue.offer(new DelayEvent(System.currentTimeMillis() + 2000, "1"));
////
////        log.info(delayQueue.take().msg());
//    }
//
//
//    /**
//     * 在很多场景下，它能让我们实现无锁的算法。当多个线程更新一个共同的值的时候，它通常会比 AtomicLong 更合适。
//     */
//    @Test
//    public void 并发累加器() throws InterruptedException {
//        LongAccumulator accumulator = new LongAccumulator(Long::sum, 10);
//
//        Runnable runnable = () -> accumulator.accumulate(1);
//
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 50; i++) {
//            executor.submit(runnable);
//        }
//        executor.shutdown();
//
//        if (executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
//            System.out.println(accumulator.get());
//        }
//        assert accumulator.get() == 60;
//    }
//
//    @Test
//    public void 字节数组与十六进制() {
//        String hex = HexFormat.of().formatHex(new byte[]{127, 0, -50, 105});
//        log.info(hex);
//        System.out.println(Arrays.toString(HexFormat.of().parseHex(hex)));
//
//        //int 和任意进制的字符串 转换
//        int x = Integer.parseInt(hex, 16);
//        System.out.println(x);
//        assert hex.equals(Integer.toString(x, 16));
//
//    }
//
//    @Test
//    public void 数组二分查找() {
//        int[] t = new int[]{1, 2, 4, 5};
//        int x = Arrays.binarySearch(t, 3);
//        //返回    -(insertion point)-1
//        System.out.println(x); //-3
//        assert ~x == 2; //“按位非”运算符  按位取反
//        System.out.println(~2);
//
//    }
//
//    @Test
//    public void phaser() {
//        Phaser phaser = new Phaser(50);
//
//
//        Runnable r = () -> {
//            System.out.println("phase-0");
//            phaser.arriveAndAwaitAdvance(); //不管线程到这 都必须等待其他线程 到才能走
//            System.out.println("phase-1");
//            phaser.arriveAndAwaitAdvance();
//            System.out.println("phase-2");
//            phaser.arriveAndDeregister(); //到了就注销线程
//        };
//
//        ExecutorService executor = Executors.newFixedThreadPool(50);
//        for (int i = 0; i < 50; i++) {
//            executor.submit(r); //开辟50个线程执行r
//        }
//
//
//    }
//
//    /**
//     * 如果我们需要对 bit 数组进行一些操作该怎么办呢？你是不是会使用 boolean[] 来实现呢？
//     * 其实，有一种更有效、更节省内存的方法来实现。这就是 BitSet 类。BitSet 类允许我们存储和操作 bit 的数组。
//     * 与 boolean[] 相比，它消耗的内存要少 8 倍。我们可以对数组进行逻辑操作，例如：and、or、xor。
//     */
//    @Test
//    public void bitSet() {
//        BitSet bitSet1 = BitSet.valueOf(new byte[]{0, 2, 4});
//        System.out.println(bitSet1);
//
//        bitSet1.xor(new BitSet() {{
//            set(1);
//            set(2);
//            set(3);
//        }});
//
//        System.out.println(bitSet1);
//
//    }
//
//}
