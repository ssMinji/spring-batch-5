package com.ezwel.esp.batch.common.job;

import com.ezwel.esp.batch.common.listener.CommonJobListener;
import com.ezwel.esp.batch.common.tasklet.CommonSampleTasklet1;
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
public class CommonSampleJob1Config {

    private final CommonJobListener jobListener;
    private final CommonSampleTasklet1 commonSampleTasklet1;

    @Bean
    public Job commonSampleJob1(JobRepository jobRepository, Step commonSampleStep1) {
        return new JobBuilder("commonSampleJob1", jobRepository)
                .listener(jobListener)
                .start(commonSampleStep1)
                .build();
    }

    @Bean
    public Step commonSampleStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("commonSampleStep1", jobRepository)
                .tasklet(commonSampleTasklet1, transactionManager)
                .build();
    }
}
