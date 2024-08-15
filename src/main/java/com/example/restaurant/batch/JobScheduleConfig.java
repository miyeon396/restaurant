package com.example.restaurant.batch;

import com.example.restaurant.batch.config.BatchConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduleConfig {

    private final JobLauncher jobLauncher;
    private final Job etlRestaurantJob;

    @Scheduled(cron = "${schedule.job.etlRestaurantJob}")
    public void startEtlRestaurantJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("execType", "batch")
                    .addLong("startDt", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(etlRestaurantJob, jobParameters);
        } catch (Exception e) {

            log.error(e.getMessage());
        }
    }
}
