package com.ezwel.esp.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="spring.batch.job", name="enabled", havingValue = "true", matchIfMissing = true)
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer,
                                                                     JobRepository jobRepository, BatchProperties properties,
                                                                     Collection<Job> jobs, ApplicationArguments args) {
        JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);

        // Job명 체크
        String jobNames = properties.getJob().getName();
        if (StringUtils.hasText(jobNames)) {
            if (jobs.stream().map(Job::getName).noneMatch(s -> s.equals(jobNames))){
                throw new IllegalArgumentException(jobNames + "는 등록되지 않은 job name입니다. job name을 확인하세요.");
            }
            runner.setJobName(jobNames);
        }

        // Job Parameter 체크
        if (args != null) {
            JobParametersConverter jobParametersConverter = new DefaultJobParametersConverter();
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            for (String optionName : args.getOptionNames()) {
                jobParametersBuilder.addString(optionName, args.getOptionValues(optionName).get(0));
            }
            JobParameters jobParameters = jobParametersBuilder.toJobParameters();

            runner.setJobParametersConverter(new JobParametersConverter() {
                @Override
                public JobParameters getJobParameters(Properties properties) {
                    return jobParameters;
                }

                @Override
                public Properties getProperties(JobParameters params) {
                    return jobParametersConverter.getProperties(params);
                }
            });
        }

        return runner;
    }
}