package com.zhy.authentication.common.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：
 * jwt
 * @author cfl
 * @version 1.0
 * @date 2023/7/19 17:00
 */
public class Jwt {

    /**
     * 过期时长
     */
    private long time;

    /**
     * 过期时长单位
     */
    private TimeUnit timeUnit;

    /**
     * 生成token密钥
     */
    private String secretKey;

    public Jwt(long time, TimeUnit timeUnit, String secretKey) {
        this.time = time;
        this.timeUnit = timeUnit;
        this.secretKey = secretKey;
    }

    /**
     * 生成jwt
     * @param subject 键值对
     * @return
     */
    public String generateToken(Map subject) {
        Date now = new Date();

        Date expiration = new Date(now.getTime() + this.timeUnit.toMillis(this.time));

        ObjectMapper objectMapper = new ObjectMapper();
        String subjectJsonStr;
        try {
            subjectJsonStr = objectMapper.writeValueAsString(subject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Jwts.builder()
                .setSubject(subjectJsonStr)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    /**
     * 解析token
     * @param jwt
     * @return 创建token时的subject
     */
    public Map<String, Object> parseToken(String jwt) {
        Claims body = Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(jwt)
                .getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map map = objectMapper.readValue(body.getSubject(), Map.class);
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
