package com.example.restaurant.repository;

import com.example.restaurant.entity.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, String> {
}
