package com.zhou.test.aop.jdkDynamicProxy;

/**
 * @author eli
 * @date 2017/12/4 16:18
 */
public class DynamicProxyMain {
    public static void main(String[] args) {
        MathInstance math = DynamicProxyFactory.getMathProxy();
        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }
}
