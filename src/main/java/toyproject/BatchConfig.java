package toyproject;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import toyproject.entity.User;
import toyproject.repository.batch.BatchRepository;
import toyproject.service.BatchService;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
/**
 * batch job을 빌드하기 위한 기본 구성을 제공
 * stepScope 및 JobScope 인스턴스 생성
 * 여러 빈 생성
 *  -> JobRepository
 *  -> JobLauncher
 *  -> JobRegistry
 *  -> JobExplorer
 *  -> JobOperator
 * dataSource 및 transactionManager는 컨텍스트 내에서 제공해야 한다.
 *  -> JobRepository와 JobLauncher 인스턴스에서 사용
 */
@EnableBatchProcessing
public class BatchConfig {

    private final Logger logger = Logger.getLogger("BatchConfig");
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final BatchRepository batchRepository;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    private int chunkSize;
    @Value("${chunkSize:100}")
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
                .<User, User>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JpaPagingItemReader<User> reader() {

        logger.info(":::: start reader ::::");

        Map<String, Object> paramValue = new HashMap<>();

//        User.addAllUser(10000);

        return new JpaPagingItemReaderBuilder<User>()
                .name("test")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .queryString("select u from User u where u.id < 1001")
                .parameterValues(paramValue)
                .build();
    }

    @Bean
    public ItemProcessor<User, User> processor() {
        return item -> {
            logger.info(":::: start processor ::::");
            return item;
        };
    }

    @Bean
    public ItemWriter<User> writer() {
        return items -> {
            logger.info(":::: start writer ::::");

            JpaItemWriter<User> itemWriter = new JpaItemWriterBuilder<User>()
                    .entityManagerFactory(entityManagerFactory)
                    .usePersist(false)
                    .build();

            List<User> users = new ArrayList<>();
            for (User item : items) {
                item.setName(item.getName() + "!");
                users.add(item);
            }

            itemWriter.write(users);
        };
    }
}
