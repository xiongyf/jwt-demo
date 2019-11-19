package com.xiongyf.jwtdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@EnableScheduling
@Slf4j
public class DynamicCronJob implements SchedulingConfigurer {

    @Value("${properties.dynamicCron}")
    String dynamicCron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //业务执行代码
                System.out.println(new Date());

            }
        };

        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //执行于每一次任务的触发
                CronTrigger cronTrigger = new CronTrigger(dynamicCron);
                Date nextExecTime = cronTrigger.nextExecutionTime(triggerContext);
                return nextExecTime;
            }
        };
        taskRegistrar.addTriggerTask(task, trigger);
    }
}