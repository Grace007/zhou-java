package com.zhou.test.aop.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author eli
 * @date 2017/12/4 14:33
 */
public class AopTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/springTest/aop-annotation.xml");
        Math math = ctx.getBean("math", Math.class);
        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);

        ShowAfter showAfter = ctx.getBean(ShowAfter.class);
        System.out.println("################");
        showAfter.show();
        System.out.println("################");
    }


}
