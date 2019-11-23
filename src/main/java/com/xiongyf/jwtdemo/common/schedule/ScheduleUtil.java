package com.xiongyf.jwtdemo.common.schedule;

import com.xiongyf.jwtdemo.config.task.job.ScheduledTaskJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class ScheduleUtil {

    /**
     * 定时任务线程池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 存放已经启动的任务map
     */
    private Map<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();


    public void startSchedule(String taskKey, String cron, ScheduledTaskJob scheduledTaskJob) {
        lock.lock();
        ScheduledFuture<?> schedule;
        try {
            schedule = threadPoolTaskScheduler.schedule(scheduledTaskJob,
                    triggerContext -> {
                        CronTrigger cronTrigger = new CronTrigger(cron);
                        return cronTrigger.nextExecutionTime(triggerContext);
                    });
            scheduledFutureMap.put(taskKey, schedule);
        } finally {
            lock.unlock();
        }
    }

    public void stopSchedule(String taskKey, ScheduledFuture scheduledFuture) {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
        scheduledFutureMap.remove(taskKey);
    }

    public Map<String, ScheduledFuture> getScheduledFutureMap() {
        return scheduledFutureMap;
    }
}
