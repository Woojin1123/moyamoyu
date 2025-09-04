package com.moyamoyu.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import static com.moyamoyu.util.JwtUtil.REFRESH_TOKEN_EXP;

@Component
public class CookieUtil {
    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    public void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .httpOnly(true)
//                .secure(false) // 테스트 환경시 https아님
                .path("/")
                .maxAge(REFRESH_TOKEN_EXP)
                .sameSite("Lax")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void expireRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie expiredCookie = ResponseCookie.from(REFRESH_TOKEN_NAME, "")
                .httpOnly(true)
                //.secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("LAX")
                .build();
        response.addHeader("Set-Cookie", expiredCookie.toString());
    }

    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if (REFRESH_TOKEN_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
