package com.xiongyf.jwtdemo.system.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyf.jwtdemo.system.pojo.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class JwtUtil {

    public static final String JWT_KEY_NAME = "_jwt";

    public static final String USER_KEY_NAME = "jwt_user";

    /**
     * JWT的签名的字符串（非BASE64）
     */
    private static final String JWT_SIGNING_KEY_STRING = "hgkgjdfsdfsadgdfsjuytyt5959521854";

    /**
     * 默认有限时长
     */
    public static final Long JWT_DEFAULT_EXPIRATION_MILLIS = 30 * 60 * 1000L;

    /**
     * 默认刷新间隔时间
     */
    private static final Long JWT_DEFAULT_TO_REFRESH_MILLIS = 3 * 60 * 1000L;

    public static String createJwt(JwtUser jwtUser) {
        Claims claims = new DefaultClaims();
        Date created = new Date();
        Date expired = new Date(created.getTime() + JWT_DEFAULT_EXPIRATION_MILLIS);
        claims.setIssuedAt(created);
        claims.setExpiration(expired);
        claims.put(USER_KEY_NAME, jwtUser);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_SIGNING_KEY_STRING.getBytes());
        return builder.compact();

    }

    public static String getJwt(HttpServletRequest request) {
        String jwt = request.getHeader(JWT_KEY_NAME);
        if (StringUtils.isEmpty(jwt)) {
            jwt = request.getParameter(JWT_KEY_NAME);
        }
        if (StringUtils.isEmpty(jwt)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (JWT_KEY_NAME.equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }

        }
        return jwt;
    }

    public static Claims getClaims(String jwt) {
        return Jwts.parser().setSigningKey(JWT_SIGNING_KEY_STRING.getBytes()).parseClaimsJws(jwt).getBody();
    }

    public static JwtUser getJwtUser(HttpServletRequest request) {
        return getJwtUser(getJwt(request));
    }

    public static JwtUser getJwtUser(String jwt) {
        return getJwtUser(getClaims(jwt));
    }

    public static JwtUser getJwtUser(Claims claims) {
        Object object = claims.get(USER_KEY_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JwtUser jwtUser = objectMapper.readValue(objectMapper.writeValueAsString(object), JwtUser.class);
            return jwtUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isTimeToRefresh(Claims claims) {
        Long created = claims.getIssuedAt().getTime();
        Long current = new Date().getTime();
        if (created + JWT_DEFAULT_TO_REFRESH_MILLIS < current) {
            return true;
        }
        return false;
    }

    public static String refreshJwt(String jwt) {
        Claims claims = getClaims(jwt);
        Date created = new Date();
        Date expired = new Date(created.getTime() + JWT_DEFAULT_EXPIRATION_MILLIS);
        claims.setIssuedAt(created);
        claims.setExpiration(expired);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_SIGNING_KEY_STRING.getBytes())
                .compact();

    }

    public static String getJwtCookie(String jwt) {
        long maxAge = JWT_DEFAULT_EXPIRATION_MILLIS / 1000;
        return JWT_KEY_NAME + "=" + jwt + "; Path=/; Max-Age=" + maxAge + "; HttpOnly";

    }

    public static String getExpiredJwtCookie() {
        return JWT_KEY_NAME + "=; Path=/; Max-Age=0; HttpOnly";
    }

}
