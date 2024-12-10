package com.example.restaurant.batch.jobs.pseudonimize.step.create.reader;

import com.example.restaurant.batch.jobs.pseudonimize.constant.TransferTable;
import com.example.restaurant.common.utils.QuerydslPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;

public interface TransferReaderStrategy<E> {

    TransferTable getTableType();

    @StepScope
//    QuerydslPagingItemReader<E> createPseudonimizeReader();
    QuerydslPagingItemReader<E> createPseudonimizeReader(@Value("#{stepExecutionContext['minValue']}") Integer minValue,
                                                         @Value("#{stepExecutionContext['maxValue']}") Integer maxValue);


}
