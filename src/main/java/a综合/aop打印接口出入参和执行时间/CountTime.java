package a综合.aop打印接口出入参和执行时间;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-10 14:37
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountTime {

}
