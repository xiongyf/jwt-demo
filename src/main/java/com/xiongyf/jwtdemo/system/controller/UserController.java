package com.xiongyf.jwtdemo.system.controller;

import com.xiongyf.jwtdemo.system.pojo.JwtUser;
import com.xiongyf.jwtdemo.system.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> map = new HashMap<>();
        if ("admin".equals(username) && "123".equals(password)) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setId("1");
            jwtUser.setUsername("admin");
            String jwt = JwtUtil.createJwt(jwtUser);
            response.setHeader("Set-Cookie", JwtUtil.getJwtCookie(jwt));
            map.put("state", "1");
            map.put("msg", "登录成功！");
            return map;
        }
        map.put("state", "0");
        map.put("msg", "账号或密码错误！");
        return map;
    }

    @GetMapping("/one")
    public String one() {
        System.out.println("--one--");
        return "One";
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        try {
            response.setHeader("Set-Cookie", JwtUtil.getExpiredJwtCookie());
            response.sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/me")
    public JwtUser me(HttpServletRequest request) {
        return JwtUtil.getJwtUser(request);
    }

}
