package com.ouyang.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import 代码积累库.LombokTest;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-02-27 15:12
 */
// @SpringBootTest
@Slf4j
public class MyTest {
    @Test
    public void ds() {
        LombokTest build = LombokTest.builder()
                .hobby("1")
                .hobby("2")
                .build();
        System.out.println(build);

        System.out.println("1".split(";")[0]);
        System.out.println("1;2".split(";")[0]);

    }
}

