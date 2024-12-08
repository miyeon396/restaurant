package com.example.restaurant.batch.jobs.pseudonimize;

import com.example.restaurant.batch.item.CsvPartitioner;
import com.example.restaurant.batch.listener.EtlRestaurantJobExecutionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class PseudonimizeJobConfig {

    @Value("${etlRestaurantJob.partitioner.maxCnt}")
    private int maxCnt;


    @Bean
    public Job pseudonimizeJob(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager) {
        return new JobBuilder("pseudonimizeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(pseudonimizePartitionStep(null, null))
//                .start("partitionStep", pseudonimizePartitioner())
//                .start(create)
                .listener(new EtlRestaurantJobExecutionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step pseudonimizePartitionStep(JobRepository jobRepository,
                                          @Qualifier("createPseudonimizeStep") Step create) {
        return new StepBuilder("pseudonimizePartitionStep", jobRepository)
                .partitioner("partitionStep", new CsvPartitioner(maxCnt))
                .step(create)
                .gridSize(15)
                .taskExecutor(pseudonimizeTaskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor pseudonimizeTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("batch-task-");
        executor.initialize();
        return executor;
    }

    // 할 일
    // 초기 잡 세팅 o

    // OriginJob에서 저장된 음식점 데이터를 가져와서 데이터를 읽음
    // 일부를 가명화 처리해서 다시 저장
    // 대용량 처리에 포커스
    // 대강 200만건 전부 있는거를 저장하면 될 것으로 보임

    //local에 그냥 저장할 때 왜 중복나느지 -> PagingItemReader에 이슈가 있는걸로 보이는데
}
