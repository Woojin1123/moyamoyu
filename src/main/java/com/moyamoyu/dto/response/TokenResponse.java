package com.moyamoyu.dto.response;

public record TokenResponse(
        String refreshToken,
        String accessToken
) {
}
