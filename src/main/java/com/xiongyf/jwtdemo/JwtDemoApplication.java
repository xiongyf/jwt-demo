package com.xiongyf.jwtdemo;

import com.xiongyf.jwtdemo.config.task.job.ScheduledTaskJob;
import com.xiongyf.jwtdemo.config.task.pojo.ScheduledTaskEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Map;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class JwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(20);
		threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
		return threadPoolTaskScheduler;
	}

	/**
	 * 初始化定时任务Map
	 * key :任务key
	 * value : 执行接口实现
	 */
	@Bean(name = "scheduledTaskJobMap")
	public Map<String, ScheduledTaskJob> scheduledTaskJobMap() {
		return ScheduledTaskEnum.initScheduledTask();
	}

}

