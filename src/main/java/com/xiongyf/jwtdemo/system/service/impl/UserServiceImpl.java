package com.xiongyf.jwtdemo.system.service.impl;

import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import com.xiongyf.jwtdemo.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

    @Transactional
    @Override
    public void updateIfNot() {
        User user = userRepository.findUserById(2L);
        if (user == null) {
            System.out.println("Can't get the user");
            return;
        }
        if (user.getAge() != null) {
            System.out.println("Has been processed");
            return;
        }
        System.out.println("Hasn't been processed, updateIfNot begin...");
        user.setUpdateTime(LocalDateTime.now());
        user.setAge(10);
        userRepository.save(user);
        System.out.println("updateIfNot end");

    }
}
