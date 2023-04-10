package toyproject.springbatch;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {

    private final Logger logger = Logger.getLogger("batchConfig");
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd HH24:mm:ss");

    private int chunkSize;
    @Value("${chunkSize:10}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job job() {
        Calendar calendar = Calendar.getInstance();
        return jobBuilderFactory.get("job" + dateFormatter.format(calendar.getTime()))
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        Calendar calendar = Calendar.getInstance();
        return stepBuilderFactory.get("step" + dateFormatter.format(calendar.getTime()))
                .<String, String>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ListItemReader<String> reader() {

        logger.info(":::: start reader ::::");
        List<String> list = new ArrayList<>();

        return new ListItemReader<>(list);
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> {
            logger.info(":::: start processor ::::");
            return item;
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            logger.info(":::: start writer ::::");
        };
    }
}
