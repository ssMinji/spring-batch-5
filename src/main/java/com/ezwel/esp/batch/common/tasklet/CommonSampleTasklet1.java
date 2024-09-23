package com.ezwel.esp.batch.common.tasklet;

import com.ezwel.esp.batch.common.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonSampleTasklet1 implements Tasklet {

    private final UserMapper userMapper;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        int userId = 1;
        String userName = userMapper.selectUserName(userId);
        System.out.println("User name select: " + userName + "_selected");

        userMapper.updateUserName(userId, "NEW" + "_updated");
        userName = userMapper.selectUserName(userId);
        System.out.println("User name updated to: " + userName + "_updated");
        return RepeatStatus.FINISHED;
    }
}