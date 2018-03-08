package com.zhou.test.quartz.quartzDemo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author eli
 * @date 2017/9/27 10:53
 */
public class QuartzDemoJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(QuartzDemoJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("第一种quartz加载实现:通过监听Application启动QuartzDemoJob正在运行...");
    }
}
