package 代码积累库.aop打印接口出入参和执行时间;

import java.lang.annotation.*;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-18 12:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WebLog {
}
