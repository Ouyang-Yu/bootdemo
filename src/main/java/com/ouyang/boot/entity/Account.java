package com.ouyang.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 18:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table  //默认对应同名表,没有就创建一个同名表
public class Account {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//没有指定id 时 ,怎么自动生成
    @Id
    Integer id;

    @Column
    String name;
}
