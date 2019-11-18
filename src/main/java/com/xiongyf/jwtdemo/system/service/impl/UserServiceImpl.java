package com.xiongyf.jwtdemo.system.service.impl;

import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import com.xiongyf.jwtdemo.system.service.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;


    @Override
    public void updateIfNot() {
        List<User> users = userRepository.findByCreateTimeBetween(
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        );
        if (!CollectionUtils.isEmpty(users)) {
            System.out.println("Has been processed");
            return;
        }
        System.out.println("Hasn't been processed, updateIfNot begin...");

        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        userRepository.save(user);
        /*System.out.println("update begin");
        user.setAge(20);
        user.setUpdateTime(LocalDateTime.now());*/
        System.out.println("updateIfNot end");

    }
}
