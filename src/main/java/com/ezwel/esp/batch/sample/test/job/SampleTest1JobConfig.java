package com.ezwel.esp.batch.sample.test.job;

import com.ezwel.esp.batch.config.CommonJobListener;
import com.ezwel.esp.batch.sample.test.tasklet.SampleTest1Tasklet;
import com.ezwel.esp.batch.sample.test.tasklet.SampleTest2Tasklet;
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
public class SampleTest1JobConfig {
    private final CommonJobListener jobListener;
    private final SampleTest1Tasklet sampleTest1Tasklet;
    private final SampleTest2Tasklet sampleTest2Tasklet;

    @Bean
    public Job sampleTest1Job(JobRepository jobRepository, Step sampleTest1Step, Step sampleTest2Step) {
        return new JobBuilder("sampleTest1Job", jobRepository)
                .listener(jobListener)
                .start(sampleTest1Step)
                .next(sampleTest2Step)
                .build();
    }

    @Bean
    public Step sampleTest1Step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleTest1Step", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(sampleTest1Tasklet, transactionManager)
                .build();
    }

    @Bean
    public Step sampleTest2Step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleTest2Step", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(sampleTest2Tasklet, transactionManager)
                .build();
    }
}
