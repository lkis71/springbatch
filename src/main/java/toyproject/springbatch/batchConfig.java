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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
/**
 * batch job을 빌드하기 위한 기본 구성을 제공
 * 기본적으로 stepScope 및 JobScope 인스턴스 생성
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
public class batchConfig extends QuartzJobBean {

    private final Logger logger = Logger.getLogger("batchConfig");
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * JobInstance
     * 논리적인 Job의 실행의 단위
     */
    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime dateTime = LocalDateTime.now();
        logger.info(dateTime.toString());
        job();
    }

    /**
     * 청크 지향 처리
     * 데이터를 한 번에 하나씩 읽고 트랜잭션 경계 내에서 작성되는 청크를 생성하는 것
     * read된 데이터가 커밋 간격(chunkSize: 트랜잭션 수행 전 처리되어야 하는 아이템 개수)과 같으면 read된 chunk를 일괄적으로 writer한다.
     *
     * ## example ##
     * List items = new Arraylist();
     *
     * for (int i = 0; i < commitInterval; i++){
     *     Object item = itemReader.read()
     *     Object processedItem = itemProcessor.process(item);
     *
     *     items.add(processedItem);
     * }
     *
     * itemWriter.write(items);
     */
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<String, String>chunk(30)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ListItemReader<String> reader() {
        logger.info(":::: start reader ::::");
        List<String> list = new ArrayList<>();
        list.add("test-data1");
        list.add("test-data2");
        return new ListItemReader<>(list);
    }

    /**
     * ItemProcessor<I, O>
     * I: ItemReader에서 받아온 파라미터 타입
     * O: ItemWriter로 반활할 파라미터 타입
     */
    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> {
            logger.info(":::: start processor ::::");
            return item.toString();
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            logger.info(":::: start writer ::::");
            for (String item : items) {
                logger.info(item);
            }
        };
    }

    /**
     * ExecutionContext
     * StepExecution 개체 또는 JobExecution 개체로 범위가 지정된 영구 상태를 저장할 장소를 제공하기 위해 프레임워크에서 유지되고 제어되는 키/값 쌍의 컬렉션
     * Quartz에 익숙한 사용자에게는 JobDataMap과 매우 유사
     *
     * JobRepository
     * JobLauncher, Job, Step 구현을 위한 CRUD 작업을 제공한다.
     * 작업이 처음 시작되면 저장소에서 JobExecution을 가져온다.
     * 또한 실행 과정에서 StepExecution 및 JobExecution 구현은 레포지토리에 전달되어 유지된다.
     *
     * JobLauncher
     * JobParameters 집합으로 Job을 시작하기 위한 간단한 인터페이스스     */
}
