package com.zhou.test.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author eli
 * @date 2017/9/27 10:53
 */
public class QuartzDemoTest {
    public static SchedulerFactory schedulerFactory = new StdSchedulerFactory();


    public static void main(String[] args) throws Exception{
        //创建调度中心(调度器)
        Scheduler scheduler = schedulerFactory.getScheduler();
       //创建jobDeil(作业)
        QuartzDemoJob job = new QuartzDemoJob();
        //JobDetail的作用就是给job作业添加附加信息,比如name,group等
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity("quartzDemoJob").build();
        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("quartzDemoJob","testjob").withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
        //向调度中心注册
        scheduler.scheduleJob(jobDetail,trigger);
        // 启动调度
        scheduler.start();
    }



    public void quartzTest1() throws Exception{
        //创建调度中心(调度器)
        Scheduler scheduler = schedulerFactory.getScheduler();
        //创建jobDeil(作业)
        QuartzDemoJob job = new QuartzDemoJob();
        //JobDetail的作用就是给job作业添加附加信息,比如name,group等
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity("quartzDemoJob").build();
        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("quartzDemoJob","testjob").withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
        //向调度中心注册
        scheduler.scheduleJob(jobDetail,trigger);
        // 启动调度
        scheduler.start();
    }
}

/*
笔记
        org.quartz.core.ErrorLogger : An error occured instantiating job to be executed.....
        这是我用quartz写任务调度的时候出现的异常，Job无法实例化，以前用过N次，从来没有出现过这个问题，project中的类怎么会无法实例化呢，百思不解，思考半天感觉应该是在quartz的规则下无法实例化，而不是类本身无法instance，所以看了一下，果然，因为有很多的运行类是Team的哥哥们写的，我要做的是一个集成的任务调度，把它们都集中起来运行管理，而以前的很多的默认的空构造方法被入参的构造方法覆盖了，而quartz的默认实例化是要用默认的无参构造方法的，所以无法实例化，这样下来，当然会出现这个错误了，因为我有显示的写明默认构造方法的习惯，所以以前自己做的时候没有出现问题，既然这样，引发了我的一些其它思考，接下来分别试了多种case下的实例化问题，根据结果总结一下，希望对大家有帮助：
        1）Job类必须有默认的无参构造方法，当然不覆盖的话类本身就是无参的构造方法
        2）Job的scope必须是Public类型的，因为quartz根据反射机制实例化类，如果不是public的，无法对其暴露
        3)  Job类不能是内部类，原因同上，所以最好单独建类

        说白了，用正常的方法创建使用的时候是不会出现这样的问题的
*/
