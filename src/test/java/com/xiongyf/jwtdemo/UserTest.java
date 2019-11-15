package com.xiongyf.jwtdemo;

import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import java.time.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    UserRepository userRepository;

    @Test
    public void testLocalTimeZone() {
        LocalDateTime zonedDateTime = LocalDateTime.now(ZoneId.of("GMT"));
        System.out.println(zonedDateTime);
        assertEquals(zonedDateTime.getHour() + 8, LocalDateTime.now().getHour());
    }


    @Test
    public void testJpaBetween() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(i);
            user.setCreateTime(LocalDateTime.now().minusDays(i));
            userRepository.save(user);
        }

        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        List<User> users = userRepository.findByCreateTimeBetween(
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        );
        assertTrue(1 == users.size());
        assertEquals(users.get(0).getCreateTime().toLocalDate(), LocalDate.now());
    }

}
