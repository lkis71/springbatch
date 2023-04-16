package toyproject;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toyproject.springbatch.dto.TourGallery;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
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
 * dataSource 및 transactionMana ger는 컨텍스트 내에서 제공해야 한다.
 *  -> JobRepository와 JobLauncher 인스턴스에서 사용
 */
@EnableBatchProcessing
public class BatchConfig {

    public static final String JOB_NAME = "springBatch15";

    private final Logger logger = Logger.getLogger("BatchConfig");

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int chunkSize;
    @Value("${chunkSize:1000}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step("대전"))
                .build();
    }

    @Bean
    @JobScope
    public Step step(@Value("#{jobParameters['title']}") String title) {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<TourGallery, TourGallery>chunk(chunkSize)
                .reader(reader(title))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<TourGallery> reader(@Value("#{jobParameters['title']}") String title) {

        logger.info(":::: start reader ::::");

        Map<String, Object> paramValue = new HashMap<>();
        paramValue.put("title", "%대전%");

        return new JpaPagingItemReaderBuilder<TourGallery>()
                .name("test")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(1000)
                .queryString("SELECT t FROM TourGallery t WHERE t.galTitle LIKE :title")
                .parameterValues(paramValue)
                .build();
    }

    // build로 수정하는 방법은 애초에 존재하지 않음
    // build는 새 객체를 생성하기 때문에 수정이 아닌 등록만 됨
    
    @Bean
    public ItemProcessor<TourGallery, TourGallery> processor() {
        return item -> {

            logger.info(item.getGalWebImageUrl());

            return item;
        };
    }

    @Bean
    public ItemWriter<TourGallery> writer() {

        return new JpaItemWriterBuilder<TourGallery>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(false)
                .build();
    }
}
