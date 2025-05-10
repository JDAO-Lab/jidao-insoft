package com.inSoft.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Slf4j
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {
    
    public int coreNum = Runtime.getRuntime().availableProcessors();

    @Bean("taskExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 根据cpu核心配置核心线程池数量
        executor.setCorePoolSize(coreNum);
        // 根据cpu核心配置最大线程池数量
        executor.setMaxPoolSize(coreNum*2);
        // 等待队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(300);
        // 最大空闲时间：超过核心线程之外的线程到达200秒后会被销毁
        executor.setKeepAliveSeconds(200);
        // 拒绝策略：超过线程容量,拒绝策略设置（由调用的线程执行）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名称前缀
        executor.setThreadNamePrefix("insoftTask-");
        // 初始化线程
        executor.initialize();
        log.info("线程池 启动成功~ CPU核心："+coreNum+"个");
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
