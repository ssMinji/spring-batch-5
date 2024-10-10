package com.ezwel.esp.batch.sample.test.chunk.reader;

import com.ezwel.esp.batch.config.SqlSessionFactoryConfig;
import com.ezwel.esp.batch.sample.test.domain.User;
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
    private final int PAGE_SIZE = 4;

    public ChunkParallelItemReader(SqlSessionFactory sqlSessionFactory, @Value("#{jobParameters['status']}") String status) {
        this.setName("ChunkParallelReader"); // reader명
        this.setSqlSessionFactory(SqlSessionFactoryConfig.getSimpleExecutorSessionFactory(sqlSessionFactory));
        this.setQueryId("com.ezwel.esp.batch.sample.test.mapper.UserMapper.selectUserByStatus");
        this.setParameterValues(Map.of("status", status)); //상태가 'T'인 사용자 조회
        this.setPageSize(PAGE_SIZE);
    }

}
