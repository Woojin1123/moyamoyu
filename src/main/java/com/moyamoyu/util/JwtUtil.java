package com.moyamoyu.util;

import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static final String BEARER_PREFIX = "Bearer ";
    public static final Long REFRESH_TOKEN_EXP = 1000L * 60 * 60 * 24 * 14; // 7일;
    public static final Long ACCESS_TOKEN_EXP = 1000L * 60 * 30; // 15분

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(Long id, String email, String role) {
        long currentTimeMills = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date(currentTimeMills))
                .setExpiration(new Date(currentTimeMills + ACCESS_TOKEN_EXP))
                .signWith(key, signatureAlgorithm)
                .compact();
    }
    public String createRefreshToken(Long id, String email) {
        long currentTimeMills = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim("email", email)
                .setIssuedAt(new Date(currentTimeMills))
                .setExpiration(new Date(currentTimeMills + REFRESH_TOKEN_EXP))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new ApiException(ErrorCode.TOKEN_NOT_FOUND);
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e){
            throw new ApiException(ErrorCode.INVALID_TOKEN);
        }
    }
}
