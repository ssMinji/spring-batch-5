package com.ezwel.esp.batch.sample.test.job;

import com.ezwel.esp.batch.config.CommonJobListener;
import com.ezwel.esp.batch.config.CommonStepListener;
import com.ezwel.esp.batch.sample.test.chunk.processor.ChunkSampleItemProcessor;
import com.ezwel.esp.batch.sample.test.chunk.reader.ChunkSampleItemReader;
import com.ezwel.esp.batch.sample.test.chunk.writer.ChunkSampleItemWriter;
import com.ezwel.esp.batch.sample.test.domain.User;
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
public class ChunkSampleJobConfig {
    private final CommonJobListener commonJobListener;
    private final int CHUNK_SIZE = 1000;

    @Bean
    public Job chunkSampleJob(JobRepository jobRepository, Step chunkSampleStep) {
        return new JobBuilder("chunkSampleJob", jobRepository)
                .listener(commonJobListener)
                .start(chunkSampleStep)
                .build();
    }

    @Bean
    public Step chunkSampleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ChunkSampleItemReader chunkSampleItemReader,
                                ChunkSampleItemProcessor chunkSampleItemProcessor, ChunkSampleItemWriter chunkSampleItemWriter, CommonStepListener commonStepListener) {
        return new StepBuilder("chunkSampleStep", jobRepository)
                .allowStartIfComplete(true)
                .<User, User>chunk(CHUNK_SIZE, transactionManager)
                .reader(chunkSampleItemReader)
                .processor(chunkSampleItemProcessor)
                .writer(chunkSampleItemWriter)
                .listener(commonStepListener)
                .build();

    }
}
