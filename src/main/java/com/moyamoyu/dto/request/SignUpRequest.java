package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청 DTO")
public record SignUpRequest(
        @Schema(description = "이메일")
        String email,
        @Schema(description = "패스워드")
        String password,
        @Schema(description = "닉네임")
        String nickname,
        @Schema(description = "역할" , allowableValues = {"USER"})
        String role,
        @Schema(description = "주소")
        String address
) {
}
