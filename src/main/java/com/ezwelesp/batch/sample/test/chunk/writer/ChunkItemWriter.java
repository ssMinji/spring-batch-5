package com.ezwelesp.batch.sample.test.chunk.writer;

import com.ezwelesp.batch.sample.test.domain.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ChunkItemWriter extends MyBatisBatchItemWriter<User> {
    public ChunkItemWriter(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
        this.setStatementId("mapper.test.sample.com.ezwelesp.batch.UserMapper.insertUser");
    }

}
