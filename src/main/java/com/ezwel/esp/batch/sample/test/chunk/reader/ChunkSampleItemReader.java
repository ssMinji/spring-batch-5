package com.ezwel.esp.batch.sample.test.chunk.reader;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@StepScope
public class ChunkSampleItemReader extends MyBatisPagingItemReader<User> {
    // 1회 read 시 가져올 row 개수
    private final int PAGE_SIZE = 1000;

    public ChunkSampleItemReader(SqlSessionFactory sqlSessionFactory, @Value("#{jobParameters['userName']}") String userName) {
        this.setName("SampleItemReader"); // reader명
        this.setSqlSessionFactory(sqlSessionFactory);
        this.setQueryId("selectUserByUserName");
        this.setParameterValues(Map.of("name", userName));
        this.setPageSize(PAGE_SIZE);
    }

}
