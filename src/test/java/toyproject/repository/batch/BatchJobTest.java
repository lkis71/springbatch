package toyproject.repository.batch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import toyproject.BatchConfig;

import javax.batch.runtime.BatchStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.batch.job.names=" + BatchConfig.JOB_NAME})
public class BatchJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void job_execution_default_test() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assertions.assertThat(jobExecution.getStatus().toString()).isEqualTo(BatchStatus.COMPLETED.toString());
    }

    @Test
    public void job_execution_param_test() throws Exception {
//        JobParameters jobParameters = JobParameters.
    }
}
