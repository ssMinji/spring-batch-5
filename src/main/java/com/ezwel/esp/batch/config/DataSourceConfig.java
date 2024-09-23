package com.ezwel.esp.batch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    @ConditionalOnMissingBean(BatchDataSourceScriptDatabaseInitializer.class)
    BatchDataSourceScriptDatabaseInitializer batchDataSourceScriptDatabaseInitializer(DataSource dataSource,
                                                                                      @BatchDataSource ObjectProvider<DataSource> batchDataSource,
                                                                                      BatchProperties properties) {
        return new BatchDataSourceScriptDatabaseInitializer(batchDataSource.getIfAvailable(() -> dataSource), properties.getJdbc());
    }

    @BatchDataSource
    @ConfigurationProperties(prefix = "spring.datasource.batch.hikari")
    @Bean("batchDataSource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("batchDataSource") DataSource batchDataSource) {
        return new DataSourceTransactionManager(batchDataSource);
    }
}
