package com.example.restaurant.batch.item;

import com.example.restaurant.common.utils.CsvUtils;
import com.example.restaurant.entity.RestaurantInfo;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CsvReaderTest {
    @Value("${etlRestaurantJob.csv.path}")
    private String csvFilePath;

    @Value("${etlRestaurantJob.encoding.type}")
    private String encodingType;

    private FlatFileItemReader<RestaurantInfo> createReader(int maxItemCount) {
        FlatFileItemReader<RestaurantInfo> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(csvFilePath));
        reader.setEncoding(encodingType);

        DefaultLineMapper<RestaurantInfo> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(CsvUtils.getFieldNames(RestaurantInfo.class.getDeclaredFields()));
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<RestaurantInfo> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RestaurantInfo.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        reader.setMaxItemCount(maxItemCount);

        return reader;
    }

    @Test
    public void CSV파싱_성공테스트() throws Exception {
        FlatFileItemReader<RestaurantInfo> reader = createReader(2);
        reader.open(new ExecutionContext());

        RestaurantInfo item;
        List<RestaurantInfo> items = new ArrayList<>();
        while ((item = reader.read()) != null) {
            items.add(item);
        }

        assertFalse(items.isEmpty());
        assertEquals(2, items.size());

        reader.close();
    }

    @Test
    public void CSV파싱_실패테스트() throws Exception {
        FlatFileItemReader<RestaurantInfo> reader = createReader(10);
        reader.open(new ExecutionContext());

        assertThrows(FlatFileParseException.class, () -> {
            while(reader.read() != null) {
                // do-nothing (expect parsing error)
            }
        });
        reader.close();
    }

}