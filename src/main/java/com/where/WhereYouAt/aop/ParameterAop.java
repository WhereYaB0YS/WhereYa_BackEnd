package com.where.WhereYouAt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ParameterAop {

    //@Pointcut("execution(* com.where.WhereYouAt..*.*(..))")
    private void pointCut(){}

   // @Before("pointCut()")
    public void before(JoinPoint joinPoint ){

        // 어떤 method를 실행 했는지
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("method: "+ method.getName());

        Object[] args = joinPoint.getArgs();

        for(Object obj: args){
            System.out.println("type: "+ obj.getClass().getSimpleName());
            System.out.println("value: "+ obj);
        }
    }

   // @AfterReturning(value = "pointCut()", returning = "obj")
    public void afterReturn(JoinPoint joinPoint, Object obj){
        System.out.println("return obj");
        System.out.println(obj);
    }
}
