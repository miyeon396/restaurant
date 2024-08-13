package com.example.restaurant.repository;

import com.example.restaurant.entity.RestaurantErrorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantErrorInfoRepository extends JpaRepository<RestaurantErrorInfo, String> {
}
