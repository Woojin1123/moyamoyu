package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.request.EmailConfirmRequest;
import com.moyamoyu.dto.request.EmailVerifyRequest;
import com.moyamoyu.dto.request.LoginRequest;
import com.moyamoyu.dto.request.SignUpRequest;
import com.moyamoyu.dto.response.TokenResponse;
import com.moyamoyu.service.AuthService;
import com.moyamoyu.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        cookieUtil.createRefreshTokenCookie(response, tokenResponse.refreshToken());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "로그인 성공",
                        tokenResponse.accessToken()
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        authService.signUp(signUpRequest);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "회원가입 성공 다시 로그인 해주세요.",
                        null
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        String refreshToken = cookieUtil.extractRefreshTokenFromCookie(request);

        authService.logout(refreshToken);
        cookieUtil.expireRefreshTokenCookie(response);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "로그아웃 성공",
                        null
                ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = cookieUtil.extractRefreshTokenFromCookie(request);

        TokenResponse tokenResponse = authService.refresh(refreshToken);
        cookieUtil.createRefreshTokenCookie(response, tokenResponse.refreshToken());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "토큰 재발급 성공",
                        tokenResponse.accessToken()
                )
        );
    }

    @PostMapping("/email-verifications")
    public ResponseEntity<ApiResponse<Object>> sendEmail(
            @RequestBody EmailVerifyRequest emailVerifyRequest
    ) {
        authService.verifyEmail(emailVerifyRequest.email());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "이메일 인증 메일 발송",
                        null
                )
        );
    }

    @PostMapping("/email-verifications/confirm")
    public ResponseEntity<ApiResponse<Object>> sendEmail(
            @RequestBody EmailConfirmRequest emailConfirmRequest
    ) {
        authService.confirmEmail(emailConfirmRequest.email(), emailConfirmRequest.token());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "이메일 인증 성공",
                        null
                )
        );
    }

}
