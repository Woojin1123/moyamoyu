package com.moyamoyu.repository;

import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.moyamoyu.util.JwtUtil.REFRESH_TOKEN_EXP;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String refreshTokenPrefix = "refreshToken:";
    private final String emailVerificationPrefix = "verification:";

    public void saveRefreshToken(String email, String token) {
        String key = refreshTokenPrefix + email;
        redisTemplate.opsForValue().set(key, token, REFRESH_TOKEN_EXP, TimeUnit.MILLISECONDS);
    }

    public void deleteRefreshToken(String email) {
        String key = refreshTokenPrefix + email;
        redisTemplate.delete(key);
    }

    public String findRefreshToken(String email) {
        String key = refreshTokenPrefix + email;
        return redisTemplate.opsForValue().get(key);
    }

    public String saveEmailToken(String email) {
        String key = emailVerificationPrefix + email;
        String token = String.format("%06d", new Random().nextInt(999999));

        redisTemplate.opsForValue().set(key, token, 5, TimeUnit.MINUTES);
        return token;
    }

    public void verifyEmailToken(String email, String token) {
        String key = emailVerificationPrefix + email;

        String savedToken = redisTemplate.opsForValue().get(key);
        if (!savedToken.equals(token)) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key + ":verified", "1", 10, TimeUnit.MINUTES);
    }

    public boolean isVerified(String email){
        String key = emailVerificationPrefix + email + ":verified";
        return redisTemplate.opsForValue().get(key).equals("1");
    }
}
