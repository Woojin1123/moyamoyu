package com.moyamoyu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 반환 DTO")
public record TokenResponse(
        String refreshToken,
        String accessToken
) {
}
