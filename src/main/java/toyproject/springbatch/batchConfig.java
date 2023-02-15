package toyproject.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
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
public class batchConfig {

    private final Logger logger = Logger.getLogger("batchConfig");
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("testJob3")
//                .preventRestart() //잡의 오류 발생시 재시작을 방지(오류발생)
                .start(step())
                .build();
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
        return stepBuilderFactory.get("testStep3")
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
            for(String item : items) {
                logger.info(item);
            }
        };
    }
}
