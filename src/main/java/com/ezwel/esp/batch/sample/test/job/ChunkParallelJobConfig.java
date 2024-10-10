package com.ezwel.esp.batch.sample.test.job;

import com.ezwel.esp.batch.config.*;
import com.ezwel.esp.batch.sample.test.chunk.processor.ChunkParallelItemProcessor;
import com.ezwel.esp.batch.sample.test.chunk.reader.ChunkParallelItemReader;
import com.ezwel.esp.batch.sample.test.chunk.writer.ChunkParallelItemWriter;
import com.ezwel.esp.batch.sample.test.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ChunkParallelJobConfig {
    private final CommonJobListener commonJobListener;
    private final int CHUNK_SIZE = 2;

    @Bean
    public Job chunkParallelJob(JobRepository jobRepository, Step chunkParallelStep) {
        return new JobBuilder("chunkParallelJob", jobRepository)
                .listener(commonJobListener)
                .start(chunkParallelStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step chunkParallelStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ChunkParallelItemReader chunkParallelItemReader,
                                  ChunkParallelItemProcessor chunkParallelItemProcessor, ChunkParallelItemWriter chunkParallelItemWriter,
                                  CommonStepListener commonStepListener) {
        return new StepBuilder("chunkSampleStep", jobRepository)
                .allowStartIfComplete(true)
                .<User, User>chunk(CHUNK_SIZE, transactionManager)
                .reader(chunkParallelItemReader)
                .listener(new UserItemReadListener())
                .processor(chunkParallelItemProcessor)
                .listener(new UserItemProcessorListener())
                .writer(chunkParallelItemWriter)
                .listener(new UserItemWriterListener())
                .listener(commonStepListener)
                .taskExecutor(taskExecutor())
                .build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor  taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2); //동시에 실행할 스레드 수
        taskExecutor.setMaxPoolSize(4); // 최대 스레드 수
//        taskExecutor.setQueueCapacity(20);
//        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
