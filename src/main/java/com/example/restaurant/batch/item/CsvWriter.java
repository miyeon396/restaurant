package com.example.restaurant.batch.item;

import com.example.restaurant.entity.RestaurantInfo;
import com.example.restaurant.repository.RestaurantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<RestaurantInfo> {
    private final RestaurantInfoRepository restaurantInfoRepository;

    @Override
    public void write(Chunk<? extends RestaurantInfo> items) throws Exception {
//        List<PlayerYears> scheduleList = new ArrayList<>();
//
//        items.forEach(getScheduleDto -> {
//            scheduleList.add(getScheduleDto);
//        });

        restaurantInfoRepository.saveAll(items);

    }

}
