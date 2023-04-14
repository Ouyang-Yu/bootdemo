package com.ouyang.boot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-02-27 15:12
 */
// @SpringBootTest
    @Slf4j
public class MyTest {
    @Test
    public void time() {
        // Stream.of(1, 2, 3)
        //        // .peek(System.out::println)
        //         .peek(integer -> log.info(String.valueOf(integer)))
        //         // .map(integer -> integer * 2)
        //         .forEach(System.out::println);
        Stream.of(1, 2, 3)
                .peek(e -> log.info("peekNotExecute"+e))
                .forEach(System.out::println);
        System.out.println(Stream.of(1, 2, 3)
                .peek(e -> log.info("peekNotExecute" + e))
                .count());

    }
    @Data
    @AllArgsConstructor
    class Person{
        String name;
    }
    @Test
    public void peek() {
        System.out.println(Stream.of(new Person("ss"))
                .filter(person -> true)
                .peek(integer -> {
                    integer.setName("peek");
                    System.out.println(integer);
                }).map(person -> {
                    person.setName("map");
                    System.out.println(person);
                    return person;
                })
                .count());

    }

    @Test
    public void d() {
        System.out.println(Pattern.quote(";"));

        List<String> a = getListFromString("     ", ";");
        System.out.println(a);
        System.out.println(Arrays.toString("".split(";")));
        System.out.println(Arrays.toString("  ".split(";")));
        String s = null;
        System.out.println(Arrays.toString(s.split(";")));
    }
    public  List<String> getListFromString(String str, String delimiter) {
        if (str == null || str.isEmpty()||str.isBlank()) {
            return Collections.emptyList();
        }


        String[] arr = str.split(Pattern.quote(delimiter)); // 使用 Pattern.quote() 方法转义分隔符
        return Arrays.asList(arr);
    }
}

