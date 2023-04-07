package 代码积累库.aop打印接口出入参和执行时间;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-10 14:33
 */

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
@Slf4j

public class CountTimeAspect {
    @Pointcut("@annotation(aop打印接口出入参和执行时间.CountTime)")
    //  @Pointcut("execution(*CountTimeAspect.main())")
    public void countTime() {
    }

    @SneakyThrows
    @Around("countTime()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        long beginTime = System.currentTimeMillis();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("{}.{}方法耗时为{}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                // System.currentTimeMillis()-beginTime
                stopWatch.getTotalTimeMillis()
        );
        System.out.println("123");
        return proceed;
    }




}

