package 代码积累库.数据库;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class DB {


    @Test
    public void ruoyi() {
        //        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //        PageHelper.startPage(
        //                Convert.toInt(request.getParameter("pageNum"), 1),
        //                Convert.toInt(request.getParameter("pageSize"), 10),
        //                SqlUtil.escapeOrderBySql(request.getParameter("orderByColumn"))//非空和sql正则
        //        ).setReasonable(Convert.toBool(request.getParameter("reasonable")));
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

    @Test
    public void a() {
        System.out.println(System.currentTimeMillis());//1680591588177
        System.out.println(Long.MAX_VALUE);//9223372036854775807 = 19位
        BigInteger max = new BigInteger("9223372036854775807");
        BigInteger now = new BigInteger("1680591588177");
        BigInteger day = new BigInteger("3600000").multiply(new BigInteger("24"));
        BigInteger divide = max.subtract(now).divide(day);
        System.out.println(divide);//106751971716 天,大概2.92亿年,

        System.out.println((9223372036854775807L-1680591588177L)/(1000*60*60*24));
    }
}


