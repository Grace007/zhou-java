package com.zhou;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author eli
 * @date 2017/9/18 15:40
 */
public class ApplicationRun {
    private static Logger logger = Logger.getLogger(ApplicationRun.class);

    public static void main(String[] args) throws Exception{
        logger.info(">>>>>zhou-dome项目开始启动<<<<<<<");
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");

        logger.info(">>>>>zhou-dome项目完成启动<<<<<<<");
        System.in.read();
    }
}
