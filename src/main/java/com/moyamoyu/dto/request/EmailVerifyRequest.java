package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 인증 메일 발송 요청 DTO")
public record EmailVerifyRequest(
        @Schema(description = "이메일")
        String email
) {
}
