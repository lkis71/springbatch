package toyproject.springbatch.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobLoggerListner extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);

        log.info("Job 수행완료 {}", jobExecution);

        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            log.info("success");
        } else {
            log.info("fail");
        }
    }
}
