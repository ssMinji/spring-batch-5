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
public class SampleTest2Tasklet implements Tasklet {
    private final UserMapper userMapper;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        int userId = userMapper.selectMaxId();
        String userName = userMapper.selectUserName(userId);
        System.out.println("================================Before: " + userId + " :  " + userName + "===========");

        userName = "Tom";
        userMapper.updateUserName(userId, userName);
        System.out.println("==================================After: " + userId + " :  " + userName +  "===========");
        return RepeatStatus.FINISHED;
    }
}
