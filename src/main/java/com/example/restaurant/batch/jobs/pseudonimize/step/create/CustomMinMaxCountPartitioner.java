package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomMinMaxCountPartitioner implements Partitioner {

    private final DataSource dataSource;

    public CustomMinMaxCountPartitioner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitionMap = new HashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MIN(no), MAX(no) FROM TBL_RESTAURANT_INFO_L WHERE APV_PERM_YMD = ?")) {
            ps.setString(1, "2024-07-30"); // 조건 설정
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long min = rs.getLong(1);
                    long max = rs.getLong(2);
                    long targetSize = (max - min) / gridSize + 1;

                    for (int i = 0; i < gridSize; i++) {
                        long start = min + (i * targetSize);
                        long end = Math.min(start + targetSize - 1, max);

                        ExecutionContext context = new ExecutionContext();
                        context.putLong("minValue", start);
                        context.putLong("maxValue", end);
                        partitionMap.put("partition" + i, context);
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to calculate partition ranges", e);
        }

        return partitionMap;
    }
}
