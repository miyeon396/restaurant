package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomPartitioner implements Partitioner {

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = new HashMap<>();

        // 각 파티션에 고유한 컨텍스트 생성
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putInt("partitionNumber", i);

            // 파티션별 고유 값 설정 (예: 데이터 범위)
            context.putInt("minValue", calculateMinValue(i));
            context.putInt("maxValue", calculateMaxValue(i));

            partitions.put("partition" + i, context);
        }

        return partitions;
    }

    private int calculateMinValue(int partitionIndex) {
        // 파티션별 최소값 계산 로직
        return partitionIndex * 1000;
    }

    private int calculateMaxValue(int partitionIndex) {
        // 파티션별 최대값 계산 로직
        return (partitionIndex + 1) * 1000 - 1;
    }
}
