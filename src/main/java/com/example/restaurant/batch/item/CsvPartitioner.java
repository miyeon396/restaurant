package com.example.restaurant.batch.item;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CsvPartitioner implements Partitioner {

    private final int maxCnt;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        int min = 1;
        int max = maxCnt;

        int adjustedGridSize = Math.min(gridSize, maxCnt);
        int targetSize = (max - min) / adjustedGridSize + 1;

        for (int i = 0; i < adjustedGridSize; i++) {
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
