package com.ouyang.boot.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 19:10
 */
@SpringBootTest
public class JPATest {
    @Autowired
    private AccountRepository repository;

    @Test
    void contextLoad() {
        repository.findById(1)
                .ifPresent(System.out::println);

        Account account = new Account();
        account.setName("oyy");
        Account save = repository.save(account);
        System.out.println(save.getId());
        //自动生成的主键

        //paging
        repository.findAll(PageRequest.of(0,1)).forEach(System.out::println);
    }
}
