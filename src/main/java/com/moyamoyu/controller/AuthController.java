package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.request.EmailConfirmRequest;
import com.moyamoyu.dto.request.EmailVerifyRequest;
import com.moyamoyu.dto.request.LoginRequest;
import com.moyamoyu.dto.request.SignUpRequest;
import com.moyamoyu.dto.response.TokenResponse;
import com.moyamoyu.service.AuthService;
import com.moyamoyu.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "AuthController" , description = "인증/인가 API")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "일반 사용자 로그인 API")
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
    @Operation(summary = "SignUp", description = "일반 사용자 회원가입 API")
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
    @Operation(summary = "Logout", description = "일반 사용자 로그아웃 API")
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
    @Operation(summary = "Refresh", description = "일반 사용자 AccessToken 재발급 API")
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
    @Operation(summary = "인증 이메일 전송", description = "인증용 이메일 전송 API")
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
    @Operation(summary = "이메일 인증", description = "이메일 인증에 사용되는 API")
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
