package com.ouyang.boot.AOP;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-18 12:50
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {
    @Pointcut("@annotation(WebLog)")
    public void method() {

    }

    @Before("method()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("========================================== Start ==========================================");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("IP :{}", request.getRemoteAddr());
        log.info("URL :{}", request.getRequestURI());
        log.info("请求方式:{}", request.getMethod());

        log.info("controller类:{}", joinPoint.getSignature().getDeclaringTypeName());
        log.info("controller方法:{}", joinPoint.getSignature().getName());
        log.info("方法的入参:{}", joinPoint.getArgs());
    }

    @After("method()")
    public void doAfter() {
        log.info("=========================================== End ===========================================");
    }

    @Around("method()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        log.info("方法出参:{}", result);
        log.info("time consume:{}", System.currentTimeMillis() - start);
        return result;
    }
}
