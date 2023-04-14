package 代码积累库.quartz.boot整合quartz;

import lombok.SneakyThrows;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-05 20:30
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyQuartzJob extends QuartzJobBean {



    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context)   {
        Thread.sleep(2000);
        System.out.println(context.getScheduler().getSchedulerInstanceId());
        System.out.println("taskName" + context.getJobDetail().getKey().getName());
        System.out.println("time: "+ LocalDateTime.now());
    }
}
