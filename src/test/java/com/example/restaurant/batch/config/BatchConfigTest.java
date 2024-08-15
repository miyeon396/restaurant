package com.example.restaurant.batch.config;

import com.example.restaurant.repository.RestaurantInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@Slf4j
@ActiveProfiles("test")
class BatchConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private RestaurantInfoRepository restaurantInfoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void 잡_수행_성공테스트() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("execType", "test")
                .addLong("startDt", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        assertEquals("COMPLETED WITH SKIPS", jobExecution.getExitStatus().getExitCode());

//        List<RestaurantInfo> restaurantInfos = restaurantInfoRepository.findAll();
//        assertThat(restaurantInfos.size() >= 5);

//        String sql = "select step_name, status, commit_count, read_count, write_count from BATCH_STEP_EXECUTION where job_execution_id = ?";
//        List<Map<String, Object>> metaTable = jdbcTemplate.queryForList(sql, jobExecution.getJobId());
//        for (Map<String, Object> step : metaTable) {
//            log.info("meta table row={}", step);
//        }

    }

}