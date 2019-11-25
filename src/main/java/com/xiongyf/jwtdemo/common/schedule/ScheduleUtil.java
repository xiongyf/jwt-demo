package com.xiongyf.jwtdemo.common.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
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


    public void startSchedule(String taskKey, String cron, IScheduleTaskJob  scheduleTaskJob) {
        lock.lock();
        ScheduledFuture<?> schedule;
        try {
            schedule = threadPoolTaskScheduler.schedule(scheduleTaskJob,
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

    public Date getLastTriggerTime(String cron){
        org.quartz.CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("Calculate Date").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        Date time0 = trigger.getStartTime();
        System.out.println(time0);
        Date time1 = trigger.getFireTimeAfter(time0);
        System.out.println(time1);
        Date time2 = trigger.getFireTimeAfter(time1);
        System.out.println(time2);
        Date time3 = trigger.getFireTimeAfter(time2);
        System.out.println(time3);
        long l = time1.getTime() -(time3.getTime() -time2.getTime());
        Date date = new Date(l);
        return date;
    }

}
