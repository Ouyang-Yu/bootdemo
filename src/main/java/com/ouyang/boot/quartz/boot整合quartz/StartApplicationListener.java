package com.ouyang.boot.quartz.boot整合quartz;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-06 10:17
 */

@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Scheduler scheduler;

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger1", "group1");
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .startNow()
                    .build();

            JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        }
    }
}
