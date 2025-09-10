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
        @Schema(description = "도로명 주소")
        String roadAddress,
        @Schema(description = "상세 주소")
        String detailAddress,
        @Schema(description = "우편번호")
        String zipcode
) {
}
