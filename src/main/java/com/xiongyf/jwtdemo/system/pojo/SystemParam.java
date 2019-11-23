package com.xiongyf.jwtdemo.system.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "sys_param")
public class SystemParam {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "param_key")
    private String key;

    @Column(name = "param_value")
    private String value;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
