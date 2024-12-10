package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import com.example.restaurant.batch.jobs.pseudonimize.constant.TransferTable;
import com.example.restaurant.batch.jobs.pseudonimize.step.create.reader.RestaurantInfoReaderForTest;
import com.example.restaurant.batch.jobs.pseudonimize.step.create.reader.TransferReaderStrategy;
import com.example.restaurant.common.utils.QuerydslPagingItemReader;
import com.example.restaurant.entity.FakeRestaurantInfo;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.restaurant.entity.QRestaurantInfo.restaurantInfo;

@Configuration
@RequiredArgsConstructor
public class CreatePseudonimizeStepConfig<E> {

    private final EntityManagerFactory entityManagerFactory;
    //    private final RestaurantEntityMapper mapper;

    private final ReaderStrategyFactory<E> factory;
    private final RestaurantInfoReaderForTest reader;

    private final String TABLE_NAME = "TBL_RESTAURANT_INFO_L";

    @Bean
    public Step createPseudonimizeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        TransferReaderStrategy<E> transferReaderStrategy = factory.getTransferReaderStrategy(TransferTable.of(TABLE_NAME));

        return new StepBuilder("createPseudonimizeStep", jobRepository)
                .<Object, FakeRestaurantInfo>chunk(1000, transactionManager)
//                .reader(createPseudonimizeReader2(null, null))
                .reader(transferReaderStrategy.createPseudonimizeReader(null, null))
//                .reader(reader.createPseudonimizeReader(null, null))
//                .processor(createPseudonimizeProcessor())
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
    public QuerydslPagingItemReader<?> createPseudonimizeReader2(
            @Value("#{stepExecutionContext['minValue']}") Integer minValue,
            @Value("#{stepExecutionContext['maxValue']}") Integer maxValue
    ) {
        System.out.println("start123 = " + minValue);
        System.out.println("end123 = " + maxValue);
        return new QuerydslPagingItemReader<>(entityManagerFactory,
                1000,
                true,
                jpaQueryFactory -> jpaQueryFactory.selectFrom(restaurantInfo)
                        .where(restaurantInfo.no.between(minValue, maxValue)));
//                        .where(restaurantInfo.apvPermYmd.eq("2024-07-30")));
    }

//    @Bean
//    @StepScope
//    public QuerydslPagingItemReader<RestaurantInfo> createPseudonimizeReader(
//            @Value("#{stepExecutionContext['minValue']}") Integer minValue,
//            @Value("#{stepExecutionContext['maxValue']}") Integer maxValue
//    ) {
//        System.out.println("start = " + minValue);
//        System.out.println("end = " + maxValue);
//        return new QuerydslPagingItemReader<>(entityManagerFactory,
//                1000,
//                true,
//                jpaQueryFactory -> jpaQueryFactory.selectFrom(restaurantInfo)
//                        .where(restaurantInfo.no.between(minValue, maxValue)));
////                        .where(restaurantInfo.apvPermYmd.eq("2024-07-30")));
//    }

//    @Bean
//    @StepScope
//    public ItemProcessor<E, FakeRestaurantInfo> createPseudonimizeProcessor() {
//        return mapper::toFakeEntity;
//    }

    @Bean
    @StepScope
    public JpaItemWriter<FakeRestaurantInfo> createPseudonimizeWriter() {
        JpaItemWriter<FakeRestaurantInfo> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;

    }

    //in 1h4m31s26ms


}
