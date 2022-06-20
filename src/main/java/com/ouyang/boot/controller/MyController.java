package com.ouyang.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-01 11:31
 */


@Controller //    //controller只能mapping到templates目录下的html
public class MyController {
    @RequestMapping("/test")
    public String index(Model model) {
        model.addAttribute("msg", "<h1>hello</h1>");
        model.addAttribute("list", Arrays.asList("aaa", "bbb"));
        return "test";
    }
}
