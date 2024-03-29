// package com.ouyang.boot.entity;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.util.ObjectUtils;
// import org.springframework.util.StringUtils;
//
// import javax.persistence.criteria.Predicate;
// import java.util.List;
//
// /**
//  * @author ouyangyu369@gmail.com
//  * @time 2022-03-02 19:10
//  */
// @SpringBootTest
// public class JPATest {
//     @Autowired
//     private AccountRepository repository;
//
//     @Test
//     void contextLoad() {
//         repository.findById(1).ifPresent(System.out::println);
//
//         Account account = new Account();
//         account.name("oyy");
//
//         Account save = repository.save(account);
//
//
//         //paging
//         Page<Account> page = repository.findAll(PageRequest.of(0, 1));//页码从0开始
//         page.forEach(System.out::println);
//         page.getContent().forEach(System.out::println);//页中内容
//
//         List<Account> name = repository.findAll(Sort.by(Sort.Order.asc("name")));
//
//         String age = "";
//         int sex = 1;
//         List<Account> all = repository.findAll((Specification<Account>) (root, query, builder) -> builder.and(new Predicate[]{
//                 StringUtils.hasText(age) ? null : builder.equal(root.get("age").as(String.class), age),
//                 ObjectUtils.isEmpty(sex) ? null : builder.equal(root.get("sex").as(Integer.class), sex),
//
//         }));//条件查询
//
//     }
// }
