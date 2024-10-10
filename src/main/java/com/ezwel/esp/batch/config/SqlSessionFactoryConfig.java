package com.ezwel.esp.batch.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

public class SqlSessionFactoryConfig {

    public static SqlSessionFactory getSimpleExecutorSessionFactory(SqlSessionFactory sqlSessionFactory) {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource());
            sqlSessionFactoryBean.setConfiguration(sqlSessionFactory.getConfiguration());

            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
            sqlSessionFactoryBean.setConfiguration(configuration);

            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure ExecutorType.SIMPLE", e);
        }
    }
}
