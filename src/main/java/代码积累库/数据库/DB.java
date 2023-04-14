package 代码积累库.数据库;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class DB {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        return new MybatisPlusInterceptor() {{
            addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//分页拦截器
            addInnerInterceptor(new BlockAttackInnerInterceptor());//阻止全表更新
        }};
    }


    /**
     * 定义一个MetaObjectHandler的bean交给Spring实现字段的自动填充
     * 用@Configuration @Bean一样的
     */
    @Component
    class AutoFillHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            metaObject.setValue("createTime", LocalDateTime.now());
            metaObject.setValue("createBy","ThreadLocal");
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            metaObject.setValue("updateTime",LocalDateTime.now());
        }


    }

    /**
     * compute how long currentTimeMillis can be greater than long
     */
    @Test
    public void a() {
        System.out.println(System.currentTimeMillis());//1680591588177
        System.out.println(Long.MAX_VALUE);//9223372036854775807 = 19位


        BigInteger max = new BigInteger("9223372036854775807");
        BigInteger now = new BigInteger(String.valueOf(System.currentTimeMillis()));
        BigInteger day = new BigInteger("3600000").multiply(new BigInteger("24"));
        BigInteger divide = max.subtract(now).divide(day);
        System.out.println(divide);//106751971716 天,大概2.92亿年,

        System.out.println((9223372036854775807L-1680591588177L)/(1000*60*60*24));
    }
}


