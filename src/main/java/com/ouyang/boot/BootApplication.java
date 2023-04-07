package com.ouyang.boot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyan
 */
@SpringBootApplication
@MapperScan("com.ouyang.oot")
public class BootApplication {

    public static void main(String... args) {

        SpringApplication.run(BootApplication.class, args);


    }

}
