package com.ouyang.boot.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 2022-02-11 20:24
 * @author ouyan
 */
@Component
@ConfigurationProperties(prefix = "person")
@Data
public class User {
    private int id;
    private String name;
    private String pwd;
    static Integer sum;
    public static void main(String[] args) {


    }
}
