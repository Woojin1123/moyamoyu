package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.request.LoginRequest;
import com.moyamoyu.dto.request.SignUpRequest;
import com.moyamoyu.dto.response.LoginResponse;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
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
        LoginResponse loginResponse = authService.login(loginRequest);
        cookieUtil.createRefreshTokenCookie(response, loginResponse.refreshToken());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "로그인 성공",
                        loginResponse.accessToken()
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
            HttpServletResponse response, HttpServletRequest request
    ) {
        String refreshToken = cookieUtil.extractRefreshTokenFromCookie(request);

        if (refreshToken == null) {
            throw new ApiException(ErrorCode.TOKEN_NOT_FOUND);
        }
        authService.logout(refreshToken);
        cookieUtil.expireRefreshTokenCookie(response);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "로그아웃 성공",
                        null
                ));
    }
}
