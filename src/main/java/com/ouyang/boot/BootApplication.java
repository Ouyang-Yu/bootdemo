package com.ouyang.boot;


import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyan
 */
@SpringBootApplication

public class BootApplication {

    public static void main(String... args) {
        //ds
        SpringApplication.run(BootApplication.class, args);
        for (int i = 0; i < 34; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println();
            }
        }

    }

    @Test
    public void sd() {

    }

}
