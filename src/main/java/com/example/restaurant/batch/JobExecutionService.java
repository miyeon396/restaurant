package com.example.restaurant.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class JobExecutionService {

    private final JobLauncher jobLauncher;
    private final Job etlRestaurantJob;

    @Async
    public void startEtlRestaurantJob(JobParameters jobParameters) {
        try {
            jobLauncher.run(etlRestaurantJob, jobParameters);
        } catch (Exception e) {
            log.error("etlRestaurantException :: {}",e.getMessage());
        }
    }
}
