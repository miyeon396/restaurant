package com.example.restaurant.batch.item;

import com.example.restaurant.common.utils.CsvUtils;
import com.example.restaurant.entity.RestaurantInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@RequiredArgsConstructor
public class CsvReader {

    private static String ENCODING = "EUC-KR";
    private static String CSV_FILE_PATH = "src/main/resources/fulldata_07_24_04_P_일반음식점.csv";

    @Bean
    @StepScope
    public FlatFileItemReader<RestaurantInfo> csvContentReader(
            @Value("#{stepExecutionContext['minValue']}") Integer minValue,
            @Value("#{stepExecutionContext['maxValue']}") Integer maxValue) {

        FlatFileItemReader<RestaurantInfo> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(CSV_FILE_PATH));
        reader.setEncoding(ENCODING);

        DefaultLineMapper<RestaurantInfo> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(CsvUtils.getFieldNames(RestaurantInfo.class.getDeclaredFields()));
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<RestaurantInfo> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RestaurantInfo.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(minValue);
        reader.setMaxItemCount(maxValue - minValue + 1);

        return reader;
    }

}
