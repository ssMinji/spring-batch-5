package com.ezwel.esp.batch.config;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.springframework.batch.core.ItemProcessListener;

public class UserItemProcessorListener implements ItemProcessListener<User, User> {
    @Override
    public void beforeProcess(User item) {
        ItemProcessListener.super.beforeProcess(item);
    }

    @Override
    public void afterProcess(User item, User result) {
        System.out.println("Thread :"+ Thread.currentThread().getName() + ", Processed User : " + item.getId());
    }

    @Override
    public void onProcessError(User item, Exception e) {
        ItemProcessListener.super.onProcessError(item, e);
    }
}
