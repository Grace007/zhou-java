package com.zhou.test.aop.jdkDynamicProxy;

/**
 * @author eli
 * @date 2017/12/4 14:29
 */


/**
 * 通知类，横切逻辑
 *
 */
public class Advices {

    public void before(){
        System.out.println("----------前置通知----------");
    }

    public void after(){
        System.out.println("----------最终通知----------");
    }
}