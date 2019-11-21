package com.xiongyf.jwtdemo.config.task.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scheduled_task")
public class ScheduledTaskBean {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 任务key值 唯一
     */
    @Column(name = "task_key")
    private String taskKey;
    /**
     * 任务描述
     */
    @Column(name = "task_desc")
    private String taskDesc;
    /**
     * 任务表达式
     */
    @Column(name = "task_cron")
    private String taskCron;

    /**
     * 程序初始化是否启动 1 是 0 否
     */
    @Column(name = "init_start_flag")
    private Integer initStartFlag;

    /**
     * 当前是否已启动
     */
    @Column(name = "start_flag")
    private Integer startFlag;
}
