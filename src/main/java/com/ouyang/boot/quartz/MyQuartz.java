package com.ouyang.boot.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-05 14:27
 */
public class MyQuartz {
    @Test
    public void exe() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)//set job
                .withIdentity("job1", "group1")
                //.usingJobData("one","a")
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                //.usingJobData("two","b")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }

    @DisallowConcurrentExecution//禁止并发执行即每次执行都会同一个job对象 ,因为jobDetail 每次都会创建新的job实例
    @PersistJobDataAfterExecution //对保存在jobDetail中的数据持久化,比如count变量,会保存上一次执行结果
     class MyJob implements Job {

        private String name;

        public void setName(String name) {
            this.name = name;//在jobDetail 中可以设置key为"name"的 value 就是变量值
        }


        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println(LocalDateTime.now().toString());
            /**
             * 两种方法获取value
             * 1.getJobDataMap 根据key取值
             * 2.定义变量set方法,接受同名key的value值
             */
//        JobDataMap jobDetailMap = context.getJobDetail().getJobDataMap();
//        JobDataMap triggerMap = context.getTrigger().getJobDataMap();
//        JobDataMap mergedMap = context.getMergedJobDataMap();
//
//        String one = jobDetailMap.getString("one");
//        String two = triggerMap.getString("two");
//        System.out.println("name = " + name);
        }
    }


}
