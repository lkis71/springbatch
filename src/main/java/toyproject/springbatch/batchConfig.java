package toyproject.springbatch;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
import toyproject.springbatch.api.TourListApiConnection;
import toyproject.springbatch.dto.TourGallery;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class batchConfig extends QuartzJobBean {

    private final Logger logger = Logger.getLogger("batchConfig");
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int chunkSize;
    @Value("${chunkSize:10}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job11")
                .start(step())
                .build();
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime dateTime = LocalDateTime.now();
        logger.info(dateTime.toString());
        job();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step11")
                .<TourGallery, TourGallery>chunk(chunkSize)
                .reader(reader())
//                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ListItemReader<TourGallery> reader() {

        logger.info(":::: start reader ::::");

        JSONObject dataBody = TourListApiConnection.getTourList();
        JSONObject items = dataBody.getJSONObject("items");
        JSONArray item = items.getJSONArray("item");

        List<TourGallery> tourGalleries = new ArrayList<>();
        for(Object data : item) {
            TourGallery tourGallery = TourGallery.setTourGalleries((JSONObject) data);
            tourGalleries.add(tourGallery);
        }

        return new ListItemReader<>(tourGalleries);
    }

//    @Bean
//    public ItemProcessor<TourGallery, TourGallery> processor() {
//        return item -> {
//            logger.info(":::: start processor ::::");
//            return item;
//        };
//    }

    @Bean
    public ItemWriter<TourGallery> writer() {
        return items -> {
            logger.info(":::: start writer ::::");
            for (TourGallery item : items) {
                logger.info(item.getGalContentId());
            }
        };
    }
}
