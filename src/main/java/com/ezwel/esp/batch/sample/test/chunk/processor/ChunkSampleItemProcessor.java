package com.ezwel.esp.batch.sample.test.chunk.processor;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ChunkSampleItemProcessor implements ItemProcessor<User, User> {
    private final String userName;

    public ChunkSampleItemProcessor(@Value("#{jobParameters['userName']}") String userName) {
        this.userName = userName;
    }

    @Override
    public User process(User item) throws Exception {
        if(item.getName().equals(userName)) {
            item.setId(999);
            item.setName("CHUNK");
        }
        return item;
    }

}
