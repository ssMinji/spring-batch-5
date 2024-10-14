package com.ezwelesp.batch.sample.test.chunk.reader;

import com.ezwelesp.batch.config.SqlSessionFactoryConfig;
import com.ezwelesp.batch.sample.test.domain.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@StepScope
public class ChunkParallelItemReader extends MyBatisPagingItemReader<User> {
    // 1회 read 시 가져올 row 개수
    private final int PAGE_SIZE = 10;

    public ChunkParallelItemReader(SqlSessionFactory sqlSessionFactory, @Value("#{jobParameters['status']}") String status) {
        this.setName("ChunkParallelReader"); // reader명
        this.setSqlSessionFactory(SqlSessionFactoryConfig.getSimpleExecutorSessionFactory(sqlSessionFactory));
        this.setQueryId("mapper.test.sample.com.ezwelesp.batch.UserMapper.selectUserByStatus");
        this.setParameterValues(Map.of("status", status));
        this.setPageSize(PAGE_SIZE);
        this.setSaveState(false);
    }

}
