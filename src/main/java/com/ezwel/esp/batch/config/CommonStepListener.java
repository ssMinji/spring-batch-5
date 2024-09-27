package com.ezwel.esp.batch.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CommonStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        String param1 = stepExecution.getJobParameters().getString("userName");
        String param2 = stepExecution.getJobParameters().getString("param2");
        System.out.println("==========================Parameter Check: param1: " + param1 + ", param2: " + param2);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("=================Step Complete============");
        return ExitStatus.COMPLETED;
    }
}
