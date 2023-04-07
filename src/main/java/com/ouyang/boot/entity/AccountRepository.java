package com.ouyang.boot.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-02 19:08
 */
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {//第二个为主键类型
    //1.利用父接口中方法
    // 2.方法命名自动实现(findBy字段)
    // 3.JPQL,类似sql,表换成实体类,字段换成类属性
    //4.原生sql绑定
    //5.specification动态条件拼接




    @Query(value = "select * from account where id =?1", nativeQuery = true)
    Account getBYId(int id);


}
