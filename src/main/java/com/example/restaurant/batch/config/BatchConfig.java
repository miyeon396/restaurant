package com.example.restaurant.batch.config;

import com.example.restaurant.batch.item.CsvReader;
import com.example.restaurant.batch.item.CsvWriter;
import com.example.restaurant.batch.listener.EtlRestaurantJobExecutionListener;
import com.example.restaurant.batch.listener.FlatFileParseExceptionItemReadListener;
import com.example.restaurant.entity.RestaurantInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final CsvReader csvReader;
    private final CsvWriter csvWriter;

    @Bean
    public Job etlRestaurantJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("etlRestaurantJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener())
                .start(fileProcessStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public EtlRestaurantJobExecutionListener jobExecutionListener() {
        return new EtlRestaurantJobExecutionListener();
    }

    @JobScope
    @Bean
    public Step fileProcessStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("fileReadWriteStep", jobRepository)
                .<RestaurantInfo, RestaurantInfo>chunk(500, transactionManager)
                .reader(csvReader.csvContentReader())
//                .processor(processor()) //TODO :: 엔티티 분리 프로세서
                .writer(csvWriter)
                .faultTolerant()
                .skipLimit(Integer.MAX_VALUE)
                .skip(FlatFileParseException.class)
                .listener(getFlatFileParseExceptionItemReadListener())
                .build();
    }

    @Bean
    public ItemReadListener<RestaurantInfo> getFlatFileParseExceptionItemReadListener() {
        return new FlatFileParseExceptionItemReadListener();
    }


}
