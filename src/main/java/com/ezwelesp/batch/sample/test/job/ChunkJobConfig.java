package com.ezwelesp.batch.sample.test.job;

import com.ezwelesp.batch.listener.CommonJobListener;
import com.ezwelesp.batch.listener.CommonStepListener;
import com.ezwelesp.batch.sample.test.chunk.processor.ChunkItemProcessor;
import com.ezwelesp.batch.sample.test.chunk.reader.ChunkItemReader;
import com.ezwelesp.batch.sample.test.chunk.writer.ChunkItemWriter;
import com.ezwelesp.batch.sample.test.domain.User;
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
//                .faultTolerant() // (필요 시 사용 옵션) 오류 발생해도 중단하지 않고 진행
//                .skip(Exception.class) // (필요 시 사용 옵션) 지정된 예외가 발생할 경우 해당 예외 건너뛰고 작업 계속 진행
//                .noSkip(RuntimeException.class) // (필요 시 사용 옵션) 지정된 예외가 발생할 경우 전체 작업 중단
//                .skipLimit(5) // 최대 건너뛸 수 있는 오류 개수
                .build();

    }
}
