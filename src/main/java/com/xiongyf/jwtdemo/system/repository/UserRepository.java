package com.xiongyf.jwtdemo.system.repository;


import com.xiongyf.jwtdemo.system.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
