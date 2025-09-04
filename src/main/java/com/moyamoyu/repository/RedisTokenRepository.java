package com.moyamoyu.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String email, String token, Long expiration) {
        String key = "refreshToken:" + email;
        redisTemplate.opsForValue().set(key, token, expiration, TimeUnit.MILLISECONDS);
    }
}
