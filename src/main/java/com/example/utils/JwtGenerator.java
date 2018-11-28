package com.example.utils;

import com.example.constant.ConstantKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author huchenqiang
 * @date 2018/8/21 14:06
 */
@Component
public class JwtGenerator {
    @Value("${app.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    public String generate(String subject, int role) {
        Map<String, Object> roleMap = new HashMap();
        roleMap.put("role", role);
        return Jwts.builder()
                .setClaims(roleMap)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationInMs))) // 设置过期时间 365 * 24 * 60 * 60秒(这里为了方便测试，所以设置了1年的过期时间，实际项目需要根据自己的情况修改)
                .signWith(SignatureAlgorithm.HS256, ConstantKey.SIGNING_KEY) //采用HS256加密算法
                .compact();
    }

    public Claims getClaimsFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(ConstantKey.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
