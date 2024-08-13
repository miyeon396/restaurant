package com.example.restaurant.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class JobExecutionController {

    private final JobExecutionService service;

    @GetMapping("/start/etl-restaurant-job")
    public String startEtlRestaurantJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("execType", "api")
                .addLong("startDt", System.currentTimeMillis())
                .toJobParameters();

        service.startEtlRestaurantJob(jobParameters);

        return "success";
    }
}
