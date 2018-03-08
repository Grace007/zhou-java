package com.zhou.test.aop.zeroconfig;

/**
 * @author eli
 * @date 2017/12/4 14:29
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 通知类，横切逻辑
 *
 */
@Component
@Aspect
public class Advices {
    @Before("execution(* com.zhou.test.aop.zeroconfig.*.*(..))")
    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        System.out.println(jp.getSignature().getName());
    }

    @After("execution(* com.zhou.test.aop.zeroconfig.*.*(..))")
    public void after(JoinPoint jp){
        System.out.println("----------最终通知----------");
    }
}