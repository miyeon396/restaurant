package com.example.restaurant.batch.item;

import com.example.restaurant.entity.RestaurantInfo;
import com.example.restaurant.repository.RestaurantInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CsvWriterTest {
    @Autowired
    private CsvWriter csvWriter;

    @Autowired
    private RestaurantInfoRepository restaurantInfoRepository;

    @Test
    @Transactional
    public void 파싱결과_저장_성공테스트() throws Exception {
        RestaurantInfo restaurantInfo1 = new RestaurantInfo();
        restaurantInfo1.setNo(2200000L);
        RestaurantInfo restaurantInfo2 = new RestaurantInfo();
        restaurantInfo2.setNo(2200001L);
        List<RestaurantInfo> restaurantInfos = Arrays.asList(restaurantInfo1, restaurantInfo2);

        csvWriter.write(new Chunk<>(restaurantInfos));

        List<RestaurantInfo> savedItems = restaurantInfoRepository.findAllById(List.of("2200000", "2200001"));
        assertEquals(2, savedItems.size());
        assertTrue(savedItems.stream().anyMatch(item -> item.getNo() == 2200000L));
        assertTrue(savedItems.stream().anyMatch(item -> item.getNo() == 2200001L));
    }

}