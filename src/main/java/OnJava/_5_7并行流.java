package OnJava;

import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-22 19:48
 */
public class _5_7并行流 {
    public boolean isPrime(long n) {
        return LongStream.rangeClosed(2, ((long) Math.sqrt(n)))
                .noneMatch(i -> n % i == 0);
    }

    @Test
    public void test5_7() {

        Timer timer = new Timer();
        List<Integer> primes = Stream.iterate(2, i -> i + 1)
                .parallel()
//                .filter(this::isPrime)
                .filter(integer -> LongStream.rangeClosed(2, ((long) Math.sqrt(integer))).noneMatch(i -> integer % i == 0))
                .limit(100_000)
                .collect(Collectors.toList());
        System.out.println(primes);
//       System.out.println(timer);
//       Files.write(Paths.get(""), primes, StandardOpenOption.CREATE);
    }

    @Test
    public void test() {
        Deque<String> trace = new ConcurrentLinkedDeque<String>();

        Stream.generate(new Supplier<Integer>() {

            private int current = 0; //atomicInteger 可以保存线程安全操作

            @Override
            public Integer get() {
                trace.add(current + " "+ Thread.currentThread().getName()+"\n");
                return current++;
            }
        })
                .limit(10)
                .parallel() //并行生成,顺序不对(对于有的机器CPU把他放在同一个核心所以没问题)
                .forEach(System.out::println);
        System.out.println(trace);//运行过程中打印线程信息不好
    }
}
