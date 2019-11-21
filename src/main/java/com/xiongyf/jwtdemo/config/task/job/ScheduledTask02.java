package com.xiongyf.jwtdemo.config.task.job;

import java.util.Date;

public class ScheduledTask02 implements ScheduledTaskJob {
    /**
     * 日志
     */

    @Override
    public void run() {
        System.out.println(new Date());
        System.out.println("ScheduledTask => 02  run  当前线程名称 :" + Thread.currentThread().getName());
    }
}