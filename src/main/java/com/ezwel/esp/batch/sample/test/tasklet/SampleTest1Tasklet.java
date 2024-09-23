package com.ezwel.esp.batch.sample.test.tasklet;

import com.ezwel.esp.batch.sample.test.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleTest1Tasklet implements Tasklet {
    private final UserMapper userMapper;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        int userId = userMapper.selectMaxId();
        System.out.println("================================Max User ID: " + userId + "=============");

        userMapper.insertUserName(userId+1, "test");
        System.out.println("================================Insert     : " + userId+1 + ": " + "test============");
        return RepeatStatus.FINISHED;
    }
}
