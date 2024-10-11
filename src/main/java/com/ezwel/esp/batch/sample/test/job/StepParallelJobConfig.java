package com.ezwel.esp.batch.sample.test.job;

import com.ezwel.esp.batch.listener.CommonJobListener;
import com.ezwel.esp.batch.listener.CommonStepListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StepParallelJobConfig {
    private final CommonJobListener commonJobListener;
    private final CommonStepListener commonStepListener;

    @Bean("stepParallelJob")
    public Job stepParallelJob(@Qualifier("parallelStep1") Step parallelStep1,
                                   @Qualifier("parallelStep2") Step parallelStep2,
                                   @Qualifier("parallelStep3") Step parallelStep3,
                                   @Qualifier("parallelStep4") Step parallelStep4,
                                   @Qualifier("parallelStep5") Step parallelStep5, JobRepository jobRepository) {
        // : step1, step3, step4 동시 실행 -> step2 -> step5
        Flow flow1 = new FlowBuilder<Flow>("flow1")
                .start(parallelStep1)
                .next(parallelStep2).build();
        Flow flow2 = new FlowBuilder<Flow>("flow2")
                .start(parallelStep3)
                .build();
        Flow flow3 = new FlowBuilder<Flow>("flow3")
                .start(parallelStep4)
                .build();
        Flow parallelFlow = new FlowBuilder<Flow>("parallelFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(flow1, flow2, flow3)
                .build();

        return new JobBuilder("stepParallelJob", jobRepository)
                .listener(commonJobListener)
                .start(parallelFlow)
                .next(parallelStep5)
                .end()
                .build();
    }
    @Bean("parallelStep1")
    public Step parallelStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("parallelStep1", jobRepository)
                .listener(commonStepListener)
                .tasklet((contribution, chunkContext) -> {
                    for(int i = 1; i <= 10; ++i) {
                        System.out.println("parallelStep1 : " + i);
                        Thread.sleep(500);
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
    @Bean("parallelStep2")
    public Step parallelStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("parallelStep2", jobRepository)
                .listener(commonStepListener)
                .tasklet((contribution, chunkContext) -> {
                    for(int i = 11; i <= 20; ++i) {
                        System.out.println("parallelStep2 : " + i);
                        Thread.sleep(500);
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @Bean("parallelStep3")
    public Step parallelStep3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("parallelStep3", jobRepository)
                .listener(commonStepListener)
                .tasklet((contribution, chunkContext) -> {
                    for(int i = 21; i <= 30; ++i) {
                        System.out.println("parallelStep3 : " + i);
                        Thread.sleep(500);
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }
    @Bean("parallelStep4")
    public Step parallelStep4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return  new StepBuilder("parallelStep4", jobRepository)
                .listener(commonStepListener)
                .tasklet((contribution, chunkContext) -> {
                    for(int i = 31; i <= 40; ++i) {
                        System.out.println("parallelStep4 : " + i);
                        Thread.sleep(500);
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }
    @Bean("parallelStep5")
    public Step parallelStep5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("parallelStep5", jobRepository)
                .listener(commonStepListener)
                .tasklet((contribution, chunkContext) -> {
                    for(int i = 41; i <= 50; ++i) {
                        System.out.println("parallelStep5 : " + i);
                        Thread.sleep(500);
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }
}
