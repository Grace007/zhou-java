package com.zhou.test.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author eli
 * @date 2017/12/4 15:22
 */
@Component
@Aspect
public class AdvicesMore {
    //切点
    @Pointcut("execution(* com.zhou.test.aop.annotation.Math.a*(..))")
    public void pointcut(){
    }

    //前置通知
    @Before("pointcut()")
    public void before(JoinPoint jp){
        System.out.println(jp.getSignature().getName());
        System.out.println("----------前置通知----------");
    }

    //最终通知
    @After("pointcut()")
    public void after(JoinPoint jp){
        System.out.println("----------最终通知----------");
    }

    //环绕通知
    @Around("execution(* com.zhou.test.aop.annotation.Math.s*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println(pjp.getSignature().getName());
        System.out.println("----------环绕前置----------");
        Object result=pjp.proceed();
        System.out.println("----------环绕后置----------");
        return result;
    }

    //返回结果通知
    @AfterReturning(pointcut="execution(* com.zhou.test.aop.annotation.Math.m*(..))",returning="result")
    public void afterReturning(JoinPoint jp, Object result){
        System.out.println(jp.getSignature().getName());
        System.out.println("结果是："+result);
        System.out.println("----------返回结果----------");
    }

    //异常后通知
    @AfterThrowing(pointcut="execution(* com.zhou.test.aop.annotation.Math.d*(..))",throwing="exp")
    public void afterThrowing(JoinPoint jp,Exception exp){
        System.out.println(jp.getSignature().getName());
        System.out.println("异常消息："+exp.getMessage());
        System.out.println("----------异常通知----------");
    }
}
