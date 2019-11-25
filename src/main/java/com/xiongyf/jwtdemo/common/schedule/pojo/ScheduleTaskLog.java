package com.xiongyf.jwtdemo.common.schedule.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "schedule_task_log")
public class ScheduleTaskLog {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "schedule_type")
    private String schedule_type;

    @Column(name = "process_status")
    private String process_status;


    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
