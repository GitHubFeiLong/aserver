package com.zhy.authentication.common.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：
 *
 * @author cfl
 * @version 1.0
 * @date 2023/7/19 17:00
 */
public class JwtUtil {

    private long time;

    private TimeUnit timeUnit;

    private String secretKey;

    public JwtUtil(long time, TimeUnit timeUnit, String secretKey) {
        this.time = time;
        this.timeUnit = timeUnit;
        this.secretKey = secretKey;
    }

    public String generateToken(Object subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }


    private static final String SECRET_KEY = "yourSecretKey";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public static String generateToken(String key, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

}
