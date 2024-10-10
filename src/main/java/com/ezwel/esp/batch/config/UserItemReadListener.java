package com.ezwel.esp.batch.config;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.springframework.batch.core.ItemReadListener;

public class UserItemReadListener implements ItemReadListener<User> {
    @Override
    public void beforeRead() {}

    @Override
    public void afterRead(User user) {
        System.out.println("Thread : " + Thread.currentThread().getName() + ", Read item: " + user.getId());
    }

    @Override
    public void onReadError(Exception e) {}
}
