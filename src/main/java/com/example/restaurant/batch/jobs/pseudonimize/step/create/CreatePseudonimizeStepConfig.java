package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CreatePseudonimizeStepConfig {

    @Bean
    public Step createPseudonimizeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("createPseudonimizeStep", jobRepository)
                .<String, String>chunk(1000, transactionManager)
                .reader(createPseudonimizeReader())
                .writer(createPseudonimizeWriter())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println("Step 시작!");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println("Step 종료!");
                        return ExitStatus.COMPLETED;
                    }
                }) //TODO
                .build();
    }


    private ItemReader<String> createPseudonimizeReader() {
        return new ItemReader<>() {
            private final List<String> data = List.of("data1", "data2", "data3");
            private int index = 0;

            @Override
            public String read() {
                if (index < data.size()) {
                    return data.get(index++);
                } else {
                    return null; // 데이터가 없으면 null 반환
                }
            }
        };
    }

    private ItemWriter<String> createPseudonimizeWriter() {
        return items -> {
            for (String item : items) {
                System.out.println("처리된 데이터: " + item);
            }
        };
    }


}
