package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import com.example.restaurant.common.utils.QuerydslPagingItemReader;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.restaurant.entity.QRestaurantInfo.restaurantInfo;

@Configuration
@RequiredArgsConstructor
public class CreatePseudonimizeStepConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final RestaurantEntityMapper mapper;

    @Bean
    public Step createPseudonimizeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("createPseudonimizeStep", jobRepository)
                .<RestaurantInfo, FakeRestaurantInfo>chunk(1000, transactionManager)
                .reader(createPseudonimizeReader(null, null))
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
    public QuerydslPagingItemReader<RestaurantInfo> createPseudonimizeReader(
            @Value("#{stepExecutionContext['minValue']}") Integer minValue,
            @Value("#{stepExecutionContext['maxValue']}") Integer maxValue
    ) {
        System.out.println("start = " + minValue);
        System.out.println("end = " + maxValue);
        return new QuerydslPagingItemReader<>(entityManagerFactory,
                1000,
                true,
                jpaQueryFactory -> jpaQueryFactory.selectFrom(restaurantInfo)
                        .where(restaurantInfo.no.between(minValue, maxValue)));
//                        .where(restaurantInfo.apvPermYmd.eq("2024-07-30")));
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

    //전략으로 구현한 리더 클래스의 실제 리더에서 파티셔니이 안된다가 이슈 
    // @Component
    // public class ReaderStrategyFactory<E> {
    //     private final Map<TrasferTable, TransferReaderStrategy<E>> trasferReaderMap = new HashMap<>();

    //     public ReaderStrategyFactory(List<TransferReaderStrategy<E>> stList) {
    //         for (TransferReaderStrategy<E> st : stList) {
    //             trasferReaderMap.put(st.getTableType(), st);
    //         }
    //     }

    //     public TransferTableReaderStrategy<E> getTransferReaderStrategy(TransferTable type) {
    //         return Optional.ofNullable(trasferReaderMap.get(type)).orElseThrow(() -> new NoSuchElementException("nono : "+type));
    //     }
    // }

}
