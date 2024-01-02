package 代码积累库.数据库;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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


}


