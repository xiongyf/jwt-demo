package com.xiongyf.jwtdemo.system.service.impl;

import com.xiongyf.jwtdemo.common.schedule.ScheduleUtil;
import com.xiongyf.jwtdemo.job.UpdateUserTask;
import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import com.xiongyf.jwtdemo.system.service.SystemParamService;
import com.xiongyf.jwtdemo.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    SystemParamService systemParamService;

    @Resource
    ScheduleUtil scheduleUtil;

    private String currentUpdateUserTaskCron;

    final static String UPDATE_USER_TASK_CRON = "UPDATE_USER_TASK_CRON";

    final static String UPDATE_USER_TASK = "UPDATE_USER_TASK";

    @Override
    @Transactional
    public void saveIfNot() {

        List<User> users = userRepository.findByCreateTimeBetween(
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        );
        if (!CollectionUtils.isEmpty(users)) {
            System.out.println("Has been processed");
            return;
        }
        System.out.println("Hasn't been processed, saveIfNot begin...");
        User user = new User();
        user.setCreateTime(LocalDateTime.now());

        userRepository.save(user);
        System.out.println("saveIfNot end");

    }

    @Override
    public void listenUserJobTask() {
        String cron = systemParamService.get(UPDATE_USER_TASK_CRON);
        ScheduledFuture scheduledFuture = scheduleUtil.getScheduledFutureMap().get(UPDATE_USER_TASK);
        if (StringUtils.isEmpty(cron) || "STOP".equalsIgnoreCase(cron) || !CronExpression.isValidExpression(cron)) {
            log.info("The cron means to stop or the cron is illegal: {}", cron);
            scheduleUtil.stopSchedule(UPDATE_USER_TASK, scheduledFuture);
            currentUpdateUserTaskCron = cron;
            log.info("UPDATE_USER_TASK stopped");
            return;
        }
        if (cron.equals(currentUpdateUserTaskCron)) {
            log.info("UPDATE_USER_TASK's cron did not changed");
            return;
        }
        log.info("UPDATE_USER_TASK's cron will change from {} to {}", currentUpdateUserTaskCron, cron);
        scheduleUtil.stopSchedule(UPDATE_USER_TASK, scheduledFuture);
        scheduleUtil.startSchedule(UPDATE_USER_TASK, cron, new UpdateUserTask());
        currentUpdateUserTaskCron = cron;
        log.info("UPDATE_USER_TASK changed the cron and started");
    }
}
