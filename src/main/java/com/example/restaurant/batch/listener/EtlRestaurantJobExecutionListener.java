package com.example.restaurant.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class EtlRestaurantJobExecutionListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        int skipCount = jobExecution.getStepExecutions().stream()
                .filter(stepExecution -> !stepExecution.getStepName().contains(":partition")) // 파티션 스텝은 제외
                .mapToInt(stepExecution -> (int) stepExecution.getSkipCount())
                .sum();

        if (skipCount > 0) {
            log.info("Skip Count :: {}", skipCount);
            jobExecution.setExitStatus(new ExitStatus("COMPLETED WITH SKIPS"));
        }
    }
}
