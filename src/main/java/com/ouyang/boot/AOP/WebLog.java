package com.ouyang.boot.AOP;

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
