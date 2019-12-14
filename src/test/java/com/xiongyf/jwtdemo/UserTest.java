package com.xiongyf.jwtdemo;

import com.xiongyf.jwtdemo.system.pojo.Book;
import com.xiongyf.jwtdemo.system.pojo.User;
import com.xiongyf.jwtdemo.system.repository.BookRepository;
import com.xiongyf.jwtdemo.system.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    UserRepository userRepository;

    @Resource
    BookRepository bookRepository;


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
                LocalDateTime.of(LocalDate.now(ZoneId.of("GMT")), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        );
        assertTrue(1 == users.size());
        assertEquals(users.get(0).getCreateTime().toLocalDate(), LocalDate.now());
    }


    @Test
    public void testManyToOne() {
        Book book = bookRepository.findBookById(1L);
        assertEquals(book.getOwner().getUsername(), "Tom");
        System.out.println(book);

    }

    @Test
    public void testIncreaseAgeById() {
        User user = userRepository.findUserById(5L);
        int i = userRepository.increaseAgeByIdAndUpdateTime(5L, user.getUpdateTime());
        assertEquals(1, i);
        int i2 = userRepository.increaseAgeByIdAndUpdateTime(5L, LocalDateTime.now());
        assertEquals(0, i2);
    }

}
