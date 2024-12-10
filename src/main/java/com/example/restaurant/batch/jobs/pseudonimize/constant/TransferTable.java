package com.example.restaurant.batch.jobs.pseudonimize.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransferTable {

    RESTAURANT_INFO("TBL_RESTAURANT_INFO_L"),
    FAKE_RESTAURANT_INFO("TBL_RESTAURANT_FAKE_INFO_L");

    final String tableName;

    public static TransferTable of(String tableName) {
        //TODO :: dynamic
        return RESTAURANT_INFO;
    }
}
