package 代码积累库;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-28  12:28
 */

public class String处理 {
    /**
     * string format 用法
     * William James Smiths 转化为 Smiths,William.J
     */
    public  String parseName(String name) {
        String[] words = name.split(" ");
        return String.format(
                "%s,%s.%c",
                words[2],
                words[0],
                words[1].toUpperCase().charAt(0)
        );
    }


    /**
     * Collectors的分组函数groupingBy
     * 统计str中每个字符出现次数
     */
    @Test
    public void count() {
        Stream.of("abcc".split(""))
                .collect(Collectors.groupingBy(
                        String::toString,
                        Collectors.counting()
                ))
                .forEach((k, v) -> System.out.println(k + "   :   " + v));
    }

}
