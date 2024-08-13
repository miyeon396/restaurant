package com.example.restaurant.common.utils;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static int countRows(String csvFilePath) {
        csvFilePath = "src/main/resources/test.csv";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            return (int) reader.lines().count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
