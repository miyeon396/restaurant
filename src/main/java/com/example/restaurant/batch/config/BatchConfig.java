package com.example.restaurant.batch.config;

import com.example.restaurant.batch.item.CsvPartitioner;
import com.example.restaurant.batch.item.CsvReader;
import com.example.restaurant.batch.item.CsvWriter;
import com.example.restaurant.batch.listener.EtlRestaurantJobExecutionListener;
import com.example.restaurant.batch.listener.ExceptionItemReadListener;
import com.example.restaurant.entity.RestaurantInfo;
import com.example.restaurant.repository.RestaurantErrorInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private final RestaurantErrorInfoRepository restaurantErrorInfoRepository;

    @Bean
    public Job etlRestaurantJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("etlRestaurantJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(partitionedFileProcessingStep(jobRepository, transactionManager))
                .listener(jobExecutionListener())
                .build();
    }

    @Bean
    public EtlRestaurantJobExecutionListener jobExecutionListener() {
        return new EtlRestaurantJobExecutionListener();
    }

    @Bean
    public Step partitionedFileProcessingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("partitionedFileProcessingStep", jobRepository)
                .partitioner("fileProcessingStep", csvPartitioner())
                .step(fileProcessingStep(jobRepository, transactionManager))
                .gridSize(15)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Partitioner csvPartitioner() {
        return new CsvPartitioner();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("batch-task-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Step fileProcessingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fileProcessingStep", jobRepository)
                .<RestaurantInfo, RestaurantInfo>chunk(1000, transactionManager)
                .reader(csvReader.csvContentReader(null, null))
                .writer(csvWriter)
                .faultTolerant()
                .skipLimit(Integer.MAX_VALUE)
                .skip(FlatFileParseException.class)
                .listener(getFlatFileParseExceptionItemReadListener())
                .build();
    }

    @Bean
    public ItemReadListener<RestaurantInfo> getFlatFileParseExceptionItemReadListener() {
        return new ExceptionItemReadListener(restaurantErrorInfoRepository);
    }


}
