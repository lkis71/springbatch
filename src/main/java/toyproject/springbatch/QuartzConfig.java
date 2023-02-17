package toyproject.springbatch;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final Scheduler scheduler;

    @PostConstruct
    public void run() throws SchedulerException {
        JobDetail detail = runJobDetail(batchConfig.class, new HashMap<>());

        scheduler.scheduleJob(detail, runJobTrigger("0/10 * * * * ?"));
    }

    public Trigger runJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                .build();
    }

    public JobDetail runJobDetail(Class job, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return newJob(job).usingJobData(jobDataMap).build();
    }
}
