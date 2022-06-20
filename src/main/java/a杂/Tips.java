package a杂;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-09 20:06
 */
class AAA {
    Integer a;

    public AAA(Integer a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AAA aaa = (AAA) o;
        return Objects.equals(a, aaa.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}

public class Tips {
    public static void main(String[] args) throws InterruptedException {
        List<String> a = Arrays.asList("da", "sd"); //不可变,添加元素会报错
        List<Integer> b = Stream.of(1, 20).collect(Collectors.toList());//可变

        float aa = (float) 3.4;

        //\u000d    System.out.println("asd");
        //转义了换行符 后面注释的其实会执行

        HashMap<String, AAA> map = new HashMap<>();
        AAA before = new AAA(999);
//        AAA after = new AAA(999);
        AAA after = before;
        //重写了hashcode后 这两个对象地址竟然是一样的
        //如果value 的地址不一样   key相同的话 是会覆盖原来的value的
        System.out.println(before);
        System.out.println(after);
        map.put("a", before);
        map.forEach((key, value) -> System.out.println(value));
        map.put("a", after);
        map.forEach((key, value) -> System.out.println(value));


        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1);
        /*
         * 多个线程执行 一个FutureTask的时候，只会计算一次
         *
         * FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
         *
         * // 开启两个线程计算futureTask
         * new Thread(futureTask, "AAA").start();
         * new Thread(futureTask, "BBB").start();
         * 如果我们要两个线程同时计算任务的话，那么需要这样写，需要定义两个futureTask
         *
         * FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
         * FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread2());
         *
         * // 开启两个线程计算futureTask
         * new Thread(futureTask, "AAA").start();
         *
         * new Thread(futureTask2, "BBB").start()
         */

        Executors.newFixedThreadPool(5); //定长
        Executors.newSingleThreadExecutor();//单个线程
        Executors.newCachedThreadPool(); //可缓存
        Executors.newScheduledThreadPool(2);
        System.out.println(Runtime.getRuntime().availableProcessors());
        //1

    }

}
