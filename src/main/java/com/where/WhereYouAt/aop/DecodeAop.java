package com.where.WhereYouAt.aop;

import com.where.WhereYouAt.controller.dto.user.UserDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


//@Aspect
//@Component
//public class DecodeAop {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Pointcut("execution(* com.where.WhereYouAt..*.*(..))")
//    private void pointCut(){}
//
//    @Pointcut("@annotation(com.where.WhereYouAt.annotation.Decode)")
//    private void enableDecode(){};
//
//    @Before("pointCut() && enableDecode()")
//    public void before(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();
//
//        for(Object arg: args){
//            if(arg instanceof UserDto){
//                UserDto userDto = UserDto.class.cast(arg);
//                String password = userDto.getPassword();
//                String encodedPassword = passwordEncoder.encode(password);
//                userDto.setPassword(encodedPassword);
//            }
//        }
//    }
//
//    @AfterReturning(value = "pointCut && enableDecode()", returning = "returnObj")
//    public void after(JoinPoint joinPoint, Object returnObj){
////        if(returnObj instanceof UserDto){
////            UserDto userDto = UserDto.class.cast(returnObj);
////            String password = userDto.getPassword();
////        }
//    }
//}
