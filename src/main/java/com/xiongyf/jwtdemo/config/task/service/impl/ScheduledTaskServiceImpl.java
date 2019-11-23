package com.xiongyf.jwtdemo.config.task.service.impl;

import com.xiongyf.jwtdemo.common.schedule.ScheduleUtil;
import com.xiongyf.jwtdemo.config.task.job.ScheduledTaskJob;
import com.xiongyf.jwtdemo.config.task.pojo.ScheduledTaskBean;
import com.xiongyf.jwtdemo.config.task.repository.ScheduleTaskRepository;
import com.xiongyf.jwtdemo.config.task.service.ScheduledTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    @Autowired
    private ScheduleTaskRepository scheduleTaskRepository;

    @Autowired
    ScheduleUtil scheduleUtil;
    /**
     * 所有定时任务存放Map
     * key :任务 key
     * value:任务实现
     */
    @Autowired
    @Qualifier(value = "scheduledTaskJobMap")
    private Map<String, ScheduledTaskJob> scheduledTaskJobMap;


    /**
     * 根据任务key 启动任务
     */
    @Override
    public Boolean start(String taskKey) {
        log.info(">>>>>> 启动任务 {} 开始 >>>>>>", taskKey);
        //校验是否已经启动
        if (this.isStart(taskKey)) {
            log.info(">>>>>> 当前任务已经启动，无需重复启动！");
            return false;
        }
        //校验任务是否存在
        if (!scheduledTaskJobMap.containsKey(taskKey)) {
            return false;
        }
        //根据key数据库获取任务配置信息
        ScheduledTaskBean scheduledTask = scheduleTaskRepository.findScheduleTaskBeanByTaskKey(taskKey);
        //启动任务
        this.doStartTask(scheduledTask);

        log.info(">>>>>> 启动任务 {} 结束 >>>>>>", taskKey);
        return true;
    }

    /**
     * 根据 key 停止任务
     */
    @Override
    public Boolean stop(String taskKey) {
        log.info(">>>>>> 进入停止任务 {}  >>>>>>", taskKey);
        //当前任务实例是否存在
        boolean taskStartFlag = scheduleUtil.getScheduledFutureMap().containsKey(taskKey);
        log.info(">>>>>> 当前任务实例是否存在 {}", taskStartFlag);
        if (taskStartFlag) {
            //获取任务实例
            ScheduledFuture scheduledFuture = scheduleUtil.getScheduledFutureMap().get(taskKey);
            //关闭实例
            scheduledFuture.cancel(true);
        }
        log.info(">>>>>> 结束停止任务 {}  >>>>>>", taskKey);
        return taskStartFlag;
    }

    /**
     * 根据任务key 重启任务
     */
    @Override
    public Boolean restart(String taskKey) {
        log.info(">>>>>> 进入重启任务 {}  >>>>>>", taskKey);
        //先停止
        this.stop(taskKey);
        //再启动
        return this.start(taskKey);
    }


    /**
     * 执行启动任务
     */
    private void doStartTask(ScheduledTaskBean scheduledTask) {
        //任务key
        String taskKey = scheduledTask.getTaskKey();
        //定时表达式
        String taskCron = scheduledTask.getTaskCron();
        //获取需要定时调度的接口
        ScheduledTaskJob scheduledTaskJob = scheduledTaskJobMap.get(taskKey);
        log.info(">>>>>> 任务 [ {} ] ,cron={}", scheduledTask.getTaskDesc(), taskCron);
        scheduleUtil.startSchedule(taskKey, taskCron, scheduledTaskJob);
    }


    /**
     * 任务是否已经启动
     */
    private Boolean isStart(String taskKey) {
        //校验是否已经启动
        if (scheduleUtil.getScheduledFutureMap().containsKey(taskKey)) {
            if (!scheduleUtil.getScheduledFutureMap().get(taskKey).isCancelled()) {
                return true;
            }
        }
        return false;
    }

}