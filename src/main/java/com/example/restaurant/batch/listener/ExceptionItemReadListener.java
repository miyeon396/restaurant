package com.example.restaurant.batch.listener;

import com.example.restaurant.entity.RestaurantErrorInfo;
import com.example.restaurant.entity.RestaurantInfo;
import com.example.restaurant.repository.RestaurantErrorInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

@Slf4j
public class ExceptionItemReadListener implements ItemReadListener<RestaurantInfo> {

    private final RestaurantErrorInfoRepository restaurantErrorInfoRepository;
    public ExceptionItemReadListener(RestaurantErrorInfoRepository restaurantErrorInfoRepository) {
        this.restaurantErrorInfoRepository = restaurantErrorInfoRepository;
    }

    @Override
    public void onReadError(Exception e) {
        String errRestaurantId = "";
        if (e instanceof FlatFileParseException flatFileParseException) {
            String input = flatFileParseException.getInput();
            errRestaurantId = input.split(",")[0].replaceAll("\"", "");
            log.error("Error parsing line : {}, Input value : {}",
                    flatFileParseException.getLineNumber(), input);
        }

        RestaurantErrorInfo restaurantErrorInfo = RestaurantErrorInfo.createBy(e, errRestaurantId);
        restaurantErrorInfoRepository.save(restaurantErrorInfo);
    }
}
