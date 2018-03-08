package com.zhou.test.aop.xml;

/**
 * @author eli
 * @date 2017/12/4 14:29
 */

import org.aspectj.lang.JoinPoint;

/**
 * 通知类，横切逻辑
 *
 */
public class Advices {

    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        System.out.println(jp.getSignature().getName());
    }

    public void after(JoinPoint jp){
        System.out.println("----------最终通知----------");
    }
}