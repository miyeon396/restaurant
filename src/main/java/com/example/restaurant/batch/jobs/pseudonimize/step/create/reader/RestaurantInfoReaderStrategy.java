package com.example.restaurant.batch.jobs.pseudonimize.step.create.reader;

import com.example.restaurant.batch.jobs.pseudonimize.constant.TransferTable;
import com.example.restaurant.common.utils.QuerydslPagingItemReader;
import com.example.restaurant.entity.RestaurantInfo;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.restaurant.entity.QRestaurantInfo.restaurantInfo;

//@StepScope
//@Component
@Configuration
@RequiredArgsConstructor
public class RestaurantInfoReaderStrategy implements TransferReaderStrategy<RestaurantInfo> {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public TransferTable getTableType() {
        return TransferTable.RESTAURANT_INFO;
    }

    @Override
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
//                        .where(restaurantInfo.no.between(minValue, maxValue)));
                        .where(restaurantInfo.apvPermYmd.eq("2024-07-30")));
    }
}
