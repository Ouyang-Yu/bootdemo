package com.ouyang.boot.quartz.boot整合quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-05 20:29
 */
@Configuration
public class SchedulerConfig {

    @Resource
    private DataSource dataSource;

    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        return new SchedulerFactoryBean(){{
            setSchedulerName("cluster_schedule");
            setDataSource(dataSource);
            setApplicationContextSchedulerContextKey("application");
            setQuartzProperties(properties());
            setTaskExecutor(schedulerThreadPool());
            setStartupDelay(0);
        }};


    }

    @Bean
    public Properties properties() throws IOException {
        return new PropertiesFactoryBean() {{
            setLocation(new ClassPathResource(""));
            afterPropertiesSet();
        }}.getObject();


    }

    @Bean
    public Executor schedulerThreadPool() {
        int processors = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolTaskExecutor() {{
            setCorePoolSize(processors);
            setMaxPoolSize(processors);
            setQueueCapacity(processors);
        }};

    }
}
