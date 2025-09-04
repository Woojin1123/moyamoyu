package com.moyamoyu.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String refreshTokenPrefix = "refreshToken:";
    public void save(String email, String token, Long expiration) {
        String key = refreshTokenPrefix + email;
        redisTemplate.opsForValue().set(key, token, expiration, TimeUnit.MILLISECONDS);
    }

    public void deleteRefreshToken(String email) {
        String key = refreshTokenPrefix + email;
        redisTemplate.delete(key);
    }

    public String findRefreshToken(String email) {
        String key = refreshTokenPrefix + email;
        return redisTemplate.opsForValue().get(key);
    }
}
