package com.xiongyf.jwtdemo.system.pojo;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    //@NotBlank(message = "User name can't be null")
    String username;

    @Column(name = "phone")
    //@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "Incorrect phone number format")
    private String phone;

    @Column(name = "age")
    private Integer age;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    public User() {

    }

    public User(String username, String phone, Integer age) {
        this.username = username;
        this.phone = phone;
        this.age = age;
    }
}
