package com.xiongyf.jwtdemo.system.service.impl;

import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import com.xiongyf.jwtdemo.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

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
        System.out.println("Hasn't been processed, updateIfNot begin...");
        User user = new User();
        user.setCreateTime(LocalDateTime.now());

        userRepository.save(user);
        System.out.println("updateIfNot end");

    }
}
