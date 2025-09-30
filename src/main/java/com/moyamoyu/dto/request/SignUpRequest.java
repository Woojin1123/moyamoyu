package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "회원가입 요청 DTO")
public record SignUpRequest(
        @Schema(description = "이메일")
        @Pattern(regexp = "/^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$/")
        String email,
        @Schema(description = "패스워드")
        @Pattern(regexp = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$/")
        String password,
        @Schema(description = "닉네임")
        @NotBlank
        String nickname,
        @Schema(description = "역할", allowableValues = {"USER"})
        String role,
        @Schema(description = "도로명 주소")
        @NotBlank
        String roadAddress,
        @Schema(description = "상세 주소")
        String detailAddress,
        @Schema(description = "우편번호")
        @NotNull
        String zipcode
) {
}
