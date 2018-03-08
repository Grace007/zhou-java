package com.zhou.Listener;

import com.zhou.test.SpringContextUtilTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * java注解形式配置bean
 * @author eli
 * @date 2017/11/3 12:19
 */
@Configuration
public class InitApplicationListenercConfig {
    @Bean
    public InitApplicationListener getInitApplicationListener(){
        return new InitApplicationListener();
    }
    @Bean
    public SpringContextUtilTest getTest1(){
        return new SpringContextUtilTest();
    }
}
