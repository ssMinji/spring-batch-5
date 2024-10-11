package com.ezwel.esp.batch.sample.test.job;

import com.ezwel.esp.batch.listener.CommonJobListener;
import com.ezwel.esp.batch.listener.CommonStepListener;
import com.ezwel.esp.batch.sample.test.chunk.processor.ChunkItemProcessor;
import com.ezwel.esp.batch.sample.test.chunk.reader.ChunkItemReader;
import com.ezwel.esp.batch.sample.test.chunk.writer.ChunkItemWriter;
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
public class ChunkJobConfig {
    private final CommonJobListener commonJobListener;
    private final int CHUNK_SIZE = 1000;

    @Bean("chunkJob")
    public Job chunkJob(JobRepository jobRepository, Step chunkStep) {
        return new JobBuilder("chunkJob", jobRepository)
                .listener(commonJobListener)
                .start(chunkStep)
                .build();
    }

    @Bean("chunkStep")
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ChunkItemReader chunkItemReader,
                                ChunkItemProcessor chunkItemProcessor, ChunkItemWriter chunkItemWriter, CommonStepListener commonStepListener) {
        return new StepBuilder("chunkStep", jobRepository)
                .allowStartIfComplete(true)
                .<User, User>chunk(CHUNK_SIZE, transactionManager)
                .reader(chunkItemReader)
                .processor(chunkItemProcessor)
                .writer(chunkItemWriter)
                .listener(commonStepListener)
                .build();

    }
}
