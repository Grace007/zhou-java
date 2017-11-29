package com.zhou.test.socket;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author eli
 * @date 2017/11/28 18:21
 */
public class HeartbeatJob implements Job {

    /**
     * 发送心跳包到每个client上
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
