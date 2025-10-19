package com.example.javaexecutorframework.AsyncScheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
@Slf4j
public class AsyncSchedulingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AsyncSchedulingApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 6, 2,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                new RejectedExecutionHandler() {
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
                }
        );

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                6,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        log.info("ScheduledThreadPoolExecutor..");
                        return new Thread(r, "thread " + System.nanoTime());
                    }
                }
        );

        scheduledThreadPoolExecutor.schedule(new LongRunningTask("schedule task"), 4, TimeUnit.SECONDS);

        log.info("Starting main Thread -> {}", Thread.currentThread().getName());

        for(int i = 0; i <100; ++i)
        {
            //threadPoolExecutor.submit(new LongRunningTask(i + " Smruti-Thread"));
            //Thread.sleep(1000);
        }

        log.info("Ending main Thread -> {}", Thread.currentThread().getName());
    }
}
