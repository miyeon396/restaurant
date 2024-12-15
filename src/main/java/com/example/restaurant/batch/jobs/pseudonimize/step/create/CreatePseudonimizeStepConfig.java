package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import com.example.restaurant.batch.jobs.pseudonimize.constant.TransferTable;
import com.example.restaurant.batch.jobs.pseudonimize.step.create.reader.RestaurantInfoReaderForTest;
import com.example.restaurant.batch.jobs.pseudonimize.step.create.reader.TransferReaderStrategy;
import com.example.restaurant.entity.FakeRestaurantInfo;
import com.example.restaurant.entity.RestaurantInfo;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CreatePseudonimizeStepConfig<E> {

    private final EntityManagerFactory entityManagerFactory;
    private final RestaurantEntityMapper mapper;

    private final ReaderStrategyFactory<E> factory;
    private final RestaurantInfoReaderForTest reader;

    private final String TABLE_NAME = "TBL_RESTAURANT_INFO_L";

    @Bean
    public Step createPseudonimizeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        TransferReaderStrategy<E> transferReaderStrategy = factory.getTransferReaderStrategy(TransferTable.of(TABLE_NAME));

        return new StepBuilder("createPseudonimizeStep", jobRepository)
                .<RestaurantInfo, FakeRestaurantInfo>chunk(1000, transactionManager)
                .reader(transferReaderStrategy.createPseudonimizeReader(null, null))
                .processor(createPseudonimizeProcessor())
                .writer(createPseudonimizeWriter())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println("Step 시작!");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println("Step 종료!");
                        return ExitStatus.COMPLETED;
                    }
                }) //TODO
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<RestaurantInfo, FakeRestaurantInfo> createPseudonimizeProcessor() {
        return mapper::toFakeEntity;
    }

    @Bean
    @StepScope
    public JpaItemWriter<FakeRestaurantInfo> createPseudonimizeWriter() {
        JpaItemWriter<FakeRestaurantInfo> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;

    }

    //in 1h4m31s26ms


}
