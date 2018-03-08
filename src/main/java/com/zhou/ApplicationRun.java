package com.zhou;


import com.zhou.test.quartz.springQuartz.QuartzSpringJobEntity;
import com.zhou.test.quartz.springQuartz.ScheduleUtils;
import com.zhou.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.quartz.Scheduler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author eli
 * @date 2017/9/18 15:40
 */
public class ApplicationRun {
    private static Logger logger = Logger.getLogger(ApplicationRun.class);

    public static void main(String[] args) throws Exception{
        logger.info(">>>>>zhou-demo<<<<<<<");
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
        logger.info(">>>>>zhou-demo项目完成启动<<<<<<<");
        //自动建表
        Dao nutzdao = SpringContextUtil.getBean(NutDao.class);
        //nutzdao.create(QuarzSpringJobEntity.class,false);


        QuartzSpringJobEntity quartzSpringJobEntity = nutzdao.fetch(QuartzSpringJobEntity.class);
        ScheduleUtils.run((Scheduler) SpringContextUtil.getBean("scheduler"),quartzSpringJobEntity);
        System.in.read();
    }
}
