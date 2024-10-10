package com.ezwel.esp.batch.sample.test.chunk.writer;

import com.ezwel.esp.batch.config.SqlSessionFactoryConfig;
import com.ezwel.esp.batch.sample.test.domain.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ChunkParallelItemWriter extends MyBatisBatchItemWriter<User> {

    public ChunkParallelItemWriter(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(SqlSessionFactoryConfig.getSimpleExecutorSessionFactory(sqlSessionFactory));
        this.setStatementId("com.ezwel.esp.batch.sample.test.mapper.UserMapper.updateUserStatus");
    }

}
