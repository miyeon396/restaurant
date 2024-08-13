package com.example.restaurant.batch.item;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class CsvPartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        int min = 1;
        int max = 2200000;
        int targetSize = (max - min) / gridSize + 1;

        for (int i = 0; i < gridSize; i++) {
            int start = i * targetSize + min;
            int end = Math.min(start + targetSize - 1, max);

            ExecutionContext context = new ExecutionContext();
            context.putInt("minValue", start);
            context.putInt("maxValue", end);
            result.put("partition" + i, context);
        }

        return result;
    }
}
