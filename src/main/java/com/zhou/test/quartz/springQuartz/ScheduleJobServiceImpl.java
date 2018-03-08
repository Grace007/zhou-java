package com.zhou.test.quartz.springQuartz;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl {
	@Autowired
    private Scheduler scheduler;
	@Autowired
	private NutDao nutzDao;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<QuartzSpringJobEntity> scheduleJobList = nutzDao.query(QuartzSpringJobEntity.class, Cnd.where("jobId","=",1));
		for(QuartzSpringJobEntity scheduleJob : scheduleJobList){
			/*CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }*/
		}
	}
	

    
}
