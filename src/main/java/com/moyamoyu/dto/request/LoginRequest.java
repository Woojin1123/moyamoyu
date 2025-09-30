package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청 DTO")
public record LoginRequest(
        @Schema(description = "이메일")
        @NotBlank
        String email,
        @Schema(description = "패스워드")
        @NotBlank
        String password
) {
}
