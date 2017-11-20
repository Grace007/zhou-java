//简单的任务管理类
//QuartzManager.java

package com.zhou.util;

import com.zhou.test.quartz.QuartzDemoJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.Date;

/**
 * @ClassName: QuartzManager
 * @Description: 动态添加、修改和删除定时任务管理类
 */

public class QuartzManager {
    //Scheduler:调度中心
    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "job_group_task_center";
    private static String TRIGGER_GROUP_NAME = "trigger_group_task_center";
    private static String JOb_TRIGGER = "job_trigger_";

    public static CronTrigger getTrigger(String jobName) throws SchedulerException, ParseException {
        Scheduler scheduler = sf.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            return null;
        } else
            return trigger;
    }

    /**
     *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param jobName 任务名
     * @param job     任务
     * @param time    时间设置，参考quartz说明文档
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void addJob(String jobName, Job job, String time, JobDataMap mapData)
            throws SchedulerException, ParseException {
        /** 2.2.1 */
        try {
            Scheduler scheduler = sf.getScheduler();
            // 定义调度触发规则1
            TriggerKey triggerKey = TriggerKey.triggerKey(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, JOB_GROUP_NAME).build();

                if (mapData != null) {
                    jobDetail.getJobDataMap().putAll(mapData);
                }
                //// 定义调度触发规则2
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                // 按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME)
                        .withSchedule(scheduleBuilder).build();
                //向调度中心注册job和调度器
                scheduler.scheduleJob(jobDetail, trigger);
                if (!scheduler.isShutdown())
                    scheduler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addJob(String jobName, Job job, String time) throws SchedulerException, ParseException {
        /** 2.2.1 */
        try {
            Scheduler scheduler = sf.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, JOB_GROUP_NAME).build();

                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                // 按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME)
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
                if (!scheduler.isShutdown())
                    scheduler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName
     * @param time
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void modifyJobTime(String jobName, String time, SchedulerFactoryBean schedulerFactory)
            throws SchedulerException, ParseException {
        /** 2.2.1 */
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME);
        // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);


    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName
     * @throws SchedulerException
     */
    public static void removeJob(String jobName, SchedulerFactoryBean schedulerFactory) throws SchedulerException {
        /** 2.2.1 */
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
        scheduler.deleteJob(jobKey);

    }

    /**
     * 暂停
     */
    public static void stopJob(String jobName, SchedulerFactoryBean schedulerFactory) throws SchedulerException {
        /** 2.2.1 */
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
        scheduler.pauseJob(jobKey);


    }

    /**
     * 恢复
     */
    public static void recoveryJob(String jobName, SchedulerFactoryBean schedulerFactory, String cronExpression)
            throws SchedulerException {
        /** 2.2.1 */
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
        scheduler.resumeJob(jobKey);
        /** 1.8.5 */


    }

    /**
     * 立即执行
     */
    public static void runJob(String jobName, Job job, String time, JobDataMap mapDate,
                              SchedulerFactoryBean schedulerFactory) throws SchedulerException {
        /** 2.2.1 */
        Scheduler scheduler = schedulerFactory.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(Job.class).withIdentity(jobName, JOB_GROUP_NAME).build();
            // 表达式调度构建器
            SimpleTriggerImpl simpleTriggerImpl = (SimpleTriggerImpl) TriggerBuilder.newTrigger()
                    .withIdentity(JOb_TRIGGER + jobName, TRIGGER_GROUP_NAME).build();
            scheduler.scheduleJob(jobDetail, simpleTriggerImpl);
            scheduler.addJob(jobDetail, true);
        }

        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
        // scheduler.deleteJob(jobKey);
        scheduler.triggerJob(jobKey);
        // if (!scheduler.isShutdown())
        // scheduler.start();
        /** 1.8.5 */

    }

    /**
     * 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @throws SchedulerException
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName)
            throws SchedulerException {
        /** 2.2.1 */
        Scheduler scheduler = sf.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobName);
        scheduler.deleteJob(jobKey);

    }

    /**
     * 验证quartz时间的有效性
     */
    public static boolean isValidExpression(final String cronExpression) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (ParseException e) {
        }
        return false;

    }

    public static void clearAllJobs() throws SchedulerException {
        Scheduler scheduler = sf.getScheduler();
        scheduler.clear();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(isValidExpression("1 12 12 28 8 ? 2016"));
        System.out.println(isValidExpression("0 0/10 * * * ?"));
        QuartzManager.clearAllJobs();
        Job job1 = new Job() {
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                System.out.println(1);
            }
        };
        Job job2 = new Job() {
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                System.out.println(2);
            }
        };
        Job job3 = new Job() {
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                System.out.println(3);
            }
        };
        //QuartzManager.addJob("job1", job1, "0/10 * * * * ?");
        //QuartzManager.addJob("job2", job2, "0/10 * * * * ?");
        //QuartzManager.addJob("job3", job3, "0/10 * * * * ?");
        QuartzDemoJob job4 = new QuartzDemoJob();
        QuartzManager.addJob("quartzDemoJob",job4,"*/10 * * * * ?");
        QuartzDemoJob job5 = new QuartzDemoJob();
        QuartzManager.addJob("quartzDemoJob1",job5,"*/10 * * * * ?");
        System.out.println("(QuartzManager.getTrigger = " + (QuartzManager.getTrigger("quartzDemoJob")));
        /*for (int i = 1; i <= 3; i++) {
            System.out.println(QuartzManager.getTrigger("job"+i + ""));
        }
        Thread.sleep(30000);
        QuartzManager.clearAllJobs();
        for (int i = 1; i <= 3; i++) {
            System.out.println(QuartzManager.getTrigger("job"+i + ""));
        }*/
    }
}
