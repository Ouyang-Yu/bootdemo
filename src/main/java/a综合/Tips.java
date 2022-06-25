package a综合;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-09 20:06
 */

@Slf4j
public class Tips {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*
        工厂方法出来的集合 是不可变的 除非Stream.collect
         */
        List<String> a = Arrays.asList("da", "sd"); //不可变,添加元素会报错
        List<Integer> list = List.of(1, 3);
        Map<String, Integer> map = Map.of("a", 1);
        //both immutable

        List<Integer> collect = Stream.of(1, 20).collect(Collectors.toList()) ; //可变
        collect.add(2);


        FutureTask<Integer> futureTask = new FutureTask<>(
                () -> new Random().nextInt()
        );
        new Thread(futureTask, "t3").start();
        log.debug("{}", futureTask.get()); //占位符,获取线程返回结果

        /*
        直接写的小数 都是double  其他要指定
         3.4F
         */
        float aa =  3.4F;


        //\u000d    System.out.println("asd");
        //转义了换行符 后面注释的其实会执行





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



        /*

         */
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime dateTime = nowDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();





    }

}
