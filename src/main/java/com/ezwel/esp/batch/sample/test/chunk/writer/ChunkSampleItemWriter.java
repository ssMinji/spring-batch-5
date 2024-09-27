package com.ezwel.esp.batch.sample.test.chunk.writer;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ChunkSampleItemWriter extends MyBatisBatchItemWriter<User> {
    public ChunkSampleItemWriter(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
        this.setStatementId("com.ezwel.esp.batch.sample.test.mapper.UserMapper.insertUser");
    }

}
