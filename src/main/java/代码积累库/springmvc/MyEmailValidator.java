package 代码积累库.springmvc;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/11 8:46
 */
public class MyEmailValidator implements ConstraintValidator<MyEmail, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.contains("@");
    }
}
