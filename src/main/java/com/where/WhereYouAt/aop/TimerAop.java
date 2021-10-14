package com.where.WhereYouAt.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimerAop {

    @Pointcut("execution(* com.where.WhereYouAt..*.*(..))")
    private void pointCut(){}

    //Timer 설정된 method에만 pointcut을 건다
    @Pointcut("@annotation(com.where.WhereYouAt.annotation.Timer)")
    private void enableTimer(){ };

    @Around("pointCut() && enableTimer()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 특정 object가 return되면 result에 들어간다
        Object result = joinPoint.proceed();

        stopWatch.stop();

        log.info("total time: {}",stopWatch.getTotalTimeSeconds());
        return result;
    }

}
