package com.xiongyf.jwtdemo.config.task.job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserTask implements ScheduledTaskJob {

    @Override
    public void run() {
        log.info("UpdateUserTask running  当前线程名称 :" + Thread.currentThread().getName());
    }
}