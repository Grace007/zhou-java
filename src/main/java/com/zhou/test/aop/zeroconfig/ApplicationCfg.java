package com.zhou.test.aop.zeroconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author eli
 * @date 2017/12/4 15:29
 */
@Configuration
@ComponentScan(basePackages = "com.zhou.test.aop.zeroconfig")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationCfg {

}
