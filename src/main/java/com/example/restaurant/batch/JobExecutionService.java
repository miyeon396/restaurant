package com.example.restaurant.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JobExecutionService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("etlRestaurantJob")
    Job etlRestaurantJob;

    @Autowired
    @Qualifier("pseudonimizeJob")
    Job pseudonimizeJob;


    @Async
    public void startEtlRestaurantJob(JobParameters jobParameters) {
        try {
            jobLauncher.run(etlRestaurantJob, jobParameters);
        } catch (Exception e) {
            log.error("etlRestaurantException :: {}",e.getMessage());
        }
    }

    public void startPseudonimizeJob(JobParameters jobParameters) {
        try {
            jobLauncher.run(pseudonimizeJob, jobParameters);
        } catch (Exception e) {
            log.error("pseudonimizeJobException :: {}",e.getMessage());
        }
    }
}
