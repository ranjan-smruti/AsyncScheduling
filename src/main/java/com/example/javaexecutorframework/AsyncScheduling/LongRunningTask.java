package com.example.javaexecutorframework.AsyncScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongRunningTask implements Runnable{

    private String param;

    public LongRunningTask(String param)
    {
        this.param = param;
    }

    @Override
    public void run() {
        log.info("{} Stating Task..{}", param, Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("{} Ending Task..{}", param, Thread.currentThread().getName());
    }
}
