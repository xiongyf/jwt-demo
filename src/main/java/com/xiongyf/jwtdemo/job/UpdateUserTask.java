package com.xiongyf.jwtdemo.job;

import com.xiongyf.jwtdemo.common.schedule.IScheduleTaskJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserTask implements IScheduleTaskJob {

    @Override
    public void run() {
        log.info("UpdateUserTask running  当前线程名称 :" + Thread.currentThread().getName());
    }
}