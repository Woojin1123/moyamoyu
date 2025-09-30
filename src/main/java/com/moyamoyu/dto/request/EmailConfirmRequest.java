package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "이메일 인증 요청 DTO")
public record EmailConfirmRequest(
        @Schema(description = "이메일")
        @Pattern(regexp = "/^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$/")
        String email,
        @Schema(description = "인증 토큰")
        @NotNull
        String token
) {
}
