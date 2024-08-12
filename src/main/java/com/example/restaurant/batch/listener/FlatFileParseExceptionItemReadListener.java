package com.example.restaurant.batch.listener;

import com.example.restaurant.entity.RestaurantInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

@Slf4j
public class FlatFileParseExceptionItemReadListener implements ItemReadListener<RestaurantInfo> {
    @Override
    public void onReadError(Exception ex) {
        if (ex instanceof FlatFileParseException flatFileParseException) {
            log.error("Error parsing line : {}, Input value : {}",
                    flatFileParseException.getLineNumber(), flatFileParseException.getInput());
        }
    }
}
