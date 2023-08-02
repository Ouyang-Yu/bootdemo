package com.ouyang.boot.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 18:59
 */
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
//
// @Entity
// @Table  //默认对应同名表,支持驼峰映射,没有就创建一个同名表,也可以name属性指定映射
// public class Account {
//     @Column(name = "id")
//     @GeneratedValue(strategy = GenerationType.IDENTITY)//没有指定id 时 ,怎么自动生成
//     @Id//表示主键
//     Integer id;
//
//     String name; //如果同名不需要加column
// }


@Data
@Accessors(chain = true)
public class Account {
    String id;
    String pwd;
}