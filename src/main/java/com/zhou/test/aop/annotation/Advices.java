package com.zhou.test.aop.annotation;

/**
 * @author eli
 * @date 2017/12/4 14:29
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;



/**
 * 通知类，横切逻辑
 *
 */
//@Component
//@Aspect
public class Advices {


    //@Before("execution(* com.zhou.test.aop.annotation.Math.*(..))")
    //@After("pointcut()")
    @Before("@annotation(com.zhou.test.aop.annotation.MyAnnotation)")
    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        System.out.println(jp.getSignature().getName());
    }

    //@After("execution(* com.zhou.test.aop.annotation.*.*(..))")
    //@After("pointcut()")
    @After("@annotation(com.zhou.test.aop.annotation.MyAnnotation)")
    public void after(JoinPoint jp){
        System.out.println("----------最终通知----------");
    }

    //切点 可以先定义一个切点然后复用
    @Pointcut("execution(* com.zhou.test.aop.annotation.Math.*(..))")
    public void pointcut(){
    }
}