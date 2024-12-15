package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CustomRangePartitinoer implements Partitioner {
    private final Long min;
    private final Long max;
    private final String PARTITION = "partition";

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();
        long targetSize = (max - min) / (gridSize + 1);
        long from = min;
        long to = from + targetSize + 1;

        for (int i = 0; i <= gridSize; i++) {
            ExecutionContext stepContext = new ExecutionContext();
            stepContext.put("START_SEQ_NO", from);
            stepContext.put("END_SEQ_NO", to);
            result.put(PARTITION + i, stepContext);
            from = to + 1;
            to = (i == gridSize - 1) ? max : to + targetSize;
        }
        return result;
    }
}
