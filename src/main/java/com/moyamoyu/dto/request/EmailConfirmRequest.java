package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 인증 요청 DTO")
public record EmailConfirmRequest(
        @Schema(description = "이메일")
        String email,
        @Schema(description = "인증 토큰")
        String token
) {
}
