package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {

    private JdbcOperations jdbcTemplate;

    private String table;

    private String column;

    public void setTable(String table) {
        this.table = table;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        // min, max 가져오기 (id 기준)
        int min = jdbcTemplate.queryForObject("SELECT MIN(" + column + ") from " + table, Integer.class);
        int max = jdbcTemplate.queryForObject("SELECT MAX(" + column + ") from " + table, Integer.class);

        // gridSize (4로 셋팅했음) 데이터개수 1000개일 경우
        // (1000 - 1) / 4 = targetSize는 max, min 뺀 전체 개수를 4로 나눈 값 (999 / 4 = 249 + 1 = 250)
        // 결국 전체 개수를 나눈 값이 250이니까 이렇게 수행
        int targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
        int number = 0;
        int start = min; // min
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }

            // 1 ~ 250
            // 251~ 500
            // 501 ~ 750
            // 751 ~ 1000
            value.putInt("minValue", start);
            value.putInt("maxValue", end);

            start += targetSize;
            end += targetSize;
            number++;
        }

        // key : "partition" + number
        // value : ExecutionContext (minValue, maxValue 를 가진 ExecutionContext 가 저장되겠다)
        // StepExecution에 저장될 ExecutionContext 를 gridSize 만큼 만든다.
        return result; // map 안에 key-value 로 담겨져있고, 여기서 ExecutionContext 가 담긴다.
    }
}
