package com.ezwel.esp.batch.sample.test.job;

import com.ezwel.esp.batch.listener.CommonJobListener;
import com.ezwel.esp.batch.sample.test.tasklet.Tasklet1;
import com.ezwel.esp.batch.sample.test.tasklet.Tasklet2;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TaskletJobConfig {
    private final CommonJobListener commonJobListener;
    private final Tasklet1 tasklet1;
    private final Tasklet2 tasklet2;

    @Bean("sampleTest1Job")
    public Job sampleTest1Job(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("sampleTest1Job", jobRepository)
                .listener(commonJobListener)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean("step1")
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(tasklet1, transactionManager)
                .build();
    }

    @Bean("step2")
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(tasklet2, transactionManager)
                .build();
    }
}
