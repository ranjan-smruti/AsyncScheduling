package com.example.javaexecutorframework.AsyncScheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class ThreadConfig {
    @Bean
    public TaskScheduler taskScheduler()
    {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(4);
        taskScheduler.setThreadNamePrefix("TaskScheduler-");
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean(name = "jobExecutor")
    public Executor jobExecutor()
    {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(6);
        taskExecutor.setThreadNamePrefix("AsyncTaskExec-");
        taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.info("Thread rejected... Retrying...");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        executor.submit(r);
            }
        });
        taskExecutor.initialize();
        return taskExecutor;


    }
}
