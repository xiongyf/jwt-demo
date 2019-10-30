package com.xiongyf.jwtdemo;

import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtDemoApplicationTests {

    @Resource
    private UserRepository userRepository;

    public void contextLoads() {
    }

    @Test
    public void test() {


        userRepository.save(new User("aa", "aa123456", 13));
        userRepository.save(new User("bb", "bb123456", 14));
        userRepository.save(new User("cc", "cc123456", 15));
        User bb = userRepository.findByUsername("bb");
        System.out.println(bb);

        // Assert.assertEquals(3, userRepository.findAll().size());
       // Assert.assertEquals("bb", userRepository.findByUsername("bb"));
        //userRepository.delete(userRepository.findByUsername("aa"));
    }
}
