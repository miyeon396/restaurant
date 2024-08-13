package com.example.restaurant.common;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CsvUtils {

    public static String[] getFieldNames(Field[] declaredFields) {

        List<String> fieldNames = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            fieldNames.add(declaredField.getName());
        }

        return fieldNames.toArray(String[]::new);
    }
}
