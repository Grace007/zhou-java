package com.zhou.test.aop.jdkDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**这里有个坑, 当使用jdk动态代理的时候,目标类必须要实现接口, 本类也就是工厂返回的应该是接口. 但是声明的时候应该指明是目标类
 * 否则会报错,类型转换错误:java.lang.ClassCastException: $Proxy0 cannot be cast to
 * @author eli
 * @date 2017/12/4 16:08
 */
public class DynamicProxyFactory {
    public static MathInstance getMathProxy(){
        final MathInstance mathInstance = new Math();
        final Advices advices = new Advices();
        MathInstance math = (MathInstance) Proxy.newProxyInstance(DynamicProxyFactory.class.getClassLoader(), mathInstance.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                advices.before();
                Object object = method.invoke(mathInstance,args);
                advices.after();
                return  object;

            }
        });
        return math;
    }


}
