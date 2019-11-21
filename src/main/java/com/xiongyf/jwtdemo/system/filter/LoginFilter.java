package com.xiongyf.jwtdemo.system.filter;

import com.xiongyf.jwtdemo.system.pojo.JwtUser;
import com.xiongyf.jwtdemo.system.util.JwtUtil;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*@Component
@WebFilter("/*")*/
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (uri.endsWith("/login") || uri.endsWith("/login.html") || uri.endsWith(".js") || uri.endsWith(".ico")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = JwtUtil.getJwt(request);
        Claims claims = null;
        try {
            claims = JwtUtil.getClaims(jwt);
        } catch (Exception e) {
            System.out.println("没有登录");
        }
        if (claims != null) {
            JwtUser jwtUser = JwtUtil.getJwtUser(claims);
            if (jwtUser != null) {
                // 如果超过设置的刷新时间，刷新jwt
                if (JwtUtil.isTimeToRefresh(claims)) {
                    String cookie = JwtUtil.getJwtCookie(JwtUtil.refreshJwt(jwt));
                    response.setHeader("Set-Cookie", cookie);
                }
                filterChain.doFilter(request, response);
                return;
            }
        }
        response.sendRedirect("/login.html");
    }

    @Override
    public void destroy() {

    }
}
