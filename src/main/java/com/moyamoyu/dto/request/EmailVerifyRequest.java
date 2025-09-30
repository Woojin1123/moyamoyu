package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "이메일 인증 메일 발송 요청 DTO")
public record EmailVerifyRequest(
        @Schema(description = "이메일")
        @Pattern(regexp = "/^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$/")
        String email
) {
}
