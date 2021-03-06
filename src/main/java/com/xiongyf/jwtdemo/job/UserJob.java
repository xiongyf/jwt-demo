package com.xiongyf.jwtdemo.job;

import com.xiongyf.jwtdemo.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserJob {

    @Autowired
    UserService userService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveUserIfNot() {
        /*System.out.println(LocalDateTime.now());
        userService.saveIfNot();*/
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void listenUserJobTask(){
        System.out.println(LocalDateTime.now());
        userService.listenUserJobTask();
    }
}
