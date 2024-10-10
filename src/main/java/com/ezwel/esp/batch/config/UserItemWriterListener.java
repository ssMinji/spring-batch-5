package com.ezwel.esp.batch.config;

import com.ezwel.esp.batch.sample.test.domain.User;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

public class UserItemWriterListener implements ItemWriteListener<User> {
    @Override
    public void beforeWrite(Chunk<? extends User> items) {
        ItemWriteListener.super.beforeWrite(items);
    }

    @Override
    public void afterWrite(Chunk<? extends User> items) {
        System.out.println("Thread: "+ Thread.currentThread().getName() + ", write item size : " + items.size());
        for (User user : items) {
            System.out.println("write item : " + user.getId());
        }
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends User> items) {
        ItemWriteListener.super.onWriteError(exception, items);
    }
}
