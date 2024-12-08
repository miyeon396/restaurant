package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import com.example.restaurant.entity.FakeRestaurantInfo;
import com.example.restaurant.entity.RestaurantInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")  // Spring에서 관리되는 빈으로 설정
public interface RestaurantEntityMapper {
    RestaurantEntityMapper INSTANCE = Mappers.getMapper(RestaurantEntityMapper.class);


    FakeRestaurantInfo toFakeEntity(RestaurantInfo restaurantInfo);

}
