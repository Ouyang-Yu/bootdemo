package 代码积累库;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-29  11:11
 */
public class 函数式编程 {
    @Test
    public void stream() {
        Stream.iterate(0, integer -> integer + 2).limit(10).forEach(System.out::println);
        Optional<Double> first = Stream.generate(Math::random)
                .filter(it -> it > 0.1)
                .limit(10)
                .sorted()
                .map(it -> it * 2)
                .skip(2)
                .distinct()
//                .count()
                .findFirst();

    }
}
