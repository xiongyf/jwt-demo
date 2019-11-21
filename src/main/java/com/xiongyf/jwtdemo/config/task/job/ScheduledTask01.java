package com.xiongyf.jwtdemo.config.task.job;

import java.util.Date;

public class ScheduledTask01 implements ScheduledTaskJob {

    @Override
    public void run() {
        System.out.println(new Date());
        System.out.println("ScheduledTask => 01  run  当前线程名称 :" + Thread.currentThread().getName());
    }
}