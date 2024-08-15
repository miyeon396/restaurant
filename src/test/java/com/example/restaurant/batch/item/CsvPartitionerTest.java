package com.example.restaurant.batch.item;

import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvPartitionerTest {

    @Test
    public void 총_로우수가_작은_파티셔닝_성공테스트() {
        int maxCnt = 2;
        CsvPartitioner partitioner = new CsvPartitioner(maxCnt);
        int gridSize = 10;

        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        assertEquals(maxCnt, partitions.size());
        assertEquals( 1, partitions.get("partition0").get("minValue"));
        assertEquals( 2, partitions.get("partition1").get("maxValue"));
    }

    @Test
    public void 총_로우수가_큰_파티셔닝_성공테스트() {
        int maxCnt = 220000;
        CsvPartitioner partitioner = new CsvPartitioner(maxCnt);
        int gridSize = 10;

        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        assertEquals(gridSize, partitions.size());
        assertEquals( 1, partitions.get("partition0").get("minValue"));
        assertEquals( 44000, partitions.get("partition1").get("maxValue"));
    }

}