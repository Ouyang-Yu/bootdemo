package com.ouyang.boot.controller;

import com.ouyang.boot.entity.Account;
import com.ouyang.boot.entity.AccountRepository;
import com.ouyang.boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap ;
import java.util.List;

/**
 * 2022-02-11 20:54
 *
 * @author ouyan
 */
@RestController// 将所有方法返回结果作为响应体内容
@CrossOrigin(origins = "*")
public class MyRestController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/id={id}&name={name}")
    public HashMap<String, Object> pic(@PathVariable String id,
                                       @PathVariable String name,
                                       @RequestHeader("User-Agent") String userAgent,
                                       @RequestHeader HashMap<String, String> header
                                       //@RequestAttribute("aaa") Object aaa,// 获取请求域中的属性,用于同一请求下不同页面的数据交换
                                       // @CookieValue Cookie cookie
    ) {
        HashMap<String, Object> map = new HashMap<>(10);
        map.put("id", id);
        map.put("name", name);
        map.put("userAgent", userAgent);
        map.put("header", header);
        // map.put("cookie", cookie);

        return map;
    }

    @Autowired
    User user;

    @GetMapping("/user")
    public User user() {
        return user;   //将读取到数据的配置类  输出
    }

    @GetMapping("/acc/page={page}&size={size}")
    public Page<Account> acc(@PathVariable Integer page,@PathVariable Integer size) {
        return accountRepository.findAll(PageRequest.of(page-1,size));
    }
    @GetMapping("acc")
    public List<Account> accounts() {
        return accountRepository.findAll();
    }

    @RequestMapping("/add/id={id}&name={name}")
    public String addAccount(@PathVariable Integer id, @PathVariable String name) {
        Account save = accountRepository.save(new Account(id, name));
        return "Success saved " + save.getName();
    }
}
