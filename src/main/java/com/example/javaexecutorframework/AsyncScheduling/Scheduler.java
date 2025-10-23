package com.example.javaexecutorframework.AsyncScheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

    //@Scheduled(fixedRate = 1000) //this method will now run exactly at an interval of 1sec. Not concurrent/ blocking in nature.
    @Scheduled(cron = "*/5 * * * * *") //scheduled to run in every 5 seconds.
    @Async("jobExecutor")
    void logging() throws InterruptedException {
        log.info("Scheduler started..{}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("Scheduler ended..{}",Thread.currentThread().getName());
    }

    @Scheduled(fixedDelay = 2000) //this method will now run with a delay of 2 second means new task will start after 3 sec.
    @Async("jobExecutor")
    void logging2() throws InterruptedException {
        log.info("Scheduler started with delay..{}", Thread.currentThread().getName());
        Thread.sleep(1000);
        log.info("Scheduler ended with delay..{}",Thread.currentThread().getName());
    }
}
