package com.zhou;

import com.zhou.test.SpringContextUtilTest;
import com.zhou.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author eli
 * @date 2017/9/18 15:40
 */
public class ApplicationRun {
    private static Logger logger = LoggerFactory.getLogger(ApplicationRun.class);

    public static void main(String[] args) {
        logger.info(">>>>>zhou-dome项目开始启动<<<<<<<");
        new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        SpringContextUtilTest springContextUtilTest = (SpringContextUtilTest) SpringContextUtil.getBean("springContextUtilTest");
        springContextUtilTest.show();
        logger.info(">>>>>zhou-dome项目完成启动<<<<<<<");
    }
}
