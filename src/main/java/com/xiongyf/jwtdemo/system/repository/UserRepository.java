package com.xiongyf.jwtdemo.system.repository;


import com.xiongyf.jwtdemo.system.pojo.User;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByAge(Integer age);

    @Query(value = "select * from tb_user u where u.username=? order by age", nativeQuery = true)
    List<User> findAndOrder(String username);

    List<User> findByCreateTimeBetween(LocalDateTime begin, LocalDateTime end);

}
