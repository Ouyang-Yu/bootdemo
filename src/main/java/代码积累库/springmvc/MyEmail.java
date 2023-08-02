package 代码积累库.springmvc;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/11 8:44
 */
@Documented
@Constraint(validatedBy = MyEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyEmail {
    String message() default "not right email address";
}
