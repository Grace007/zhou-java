package com.zhou.test.aop.annotation;

import org.springframework.stereotype.Component;

/**
 * @author eli
 * @date 2017/12/4 15:01
 */
@Component("shouAfter")
public class ShowAfter {
    public void show(){
        System.out.println("Hello StrUtil!");
    }
}
