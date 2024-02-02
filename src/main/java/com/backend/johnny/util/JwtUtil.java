package com.backend.johnny.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtUtil {

    public static final String JWT_PREFIX = "Bearer ";
    public static final String HEADER_STIRNG = "Authorization";
    public static final int jwtValidHour = 1;
    @Value("${jwt_secretkey}")
    String secretKey;


    public String createJwt(String value) {
        return JWT.create()
                .withClaim("expiredDate", LocalDateTime.now().plusHours(jwtValidHour).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .withClaim("username", value)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean checkExpire(String jwt) {
        String expiredDateString = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt).getClaim("expiredDate").asString();
        LocalDateTime expiredDate = LocalDateTime.parse(expiredDateString, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        return expiredDate.isBefore(LocalDateTime.now());
    }

    public boolean checkJwt(String username, String jwt) {
        String jwtGetUsername = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt).getClaim("username").asString();

        return jwtGetUsername.equals(username);
    }

}
