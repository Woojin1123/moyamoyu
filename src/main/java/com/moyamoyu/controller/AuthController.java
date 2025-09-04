package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.request.LoginRequest;
import com.moyamoyu.dto.request.SignUpRequest;
import com.moyamoyu.dto.response.LoginResponse;
import com.moyamoyu.service.AuthService;
import com.moyamoyu.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @RequestMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        LoginResponse loginResponse = authService.login(loginRequest);
        cookieUtil.createRefreshTokenCookie(response,loginResponse.refreshToken());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "로그인 성공",
                        loginResponse.accessToken()
                ));
    }

    @RequestMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> signUp(
            @RequestBody SignUpRequest signUpRequest
    ){
        authService.signUp(signUpRequest);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "회원가입 성공 다시 로그인 해주세요.",
                        null
                )
        );
    }
}
