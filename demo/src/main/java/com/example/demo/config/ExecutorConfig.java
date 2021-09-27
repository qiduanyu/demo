package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * 线程池
     *
     * @return
     */
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        // 获取cpu的线程数
        int cpu = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(2 * cpu);
        //设置最大线程数
        executor.setMaxPoolSize(3 * cpu);
        //线程池所使用的缓冲队列
        executor.setQueueCapacity(10 * cpu);
        executor.setKeepAliveSeconds(200);
        //  线程名称前缀
        executor.setThreadNamePrefix("async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

}
