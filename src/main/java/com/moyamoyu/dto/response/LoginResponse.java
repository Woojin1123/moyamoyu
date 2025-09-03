package com.moyamoyu.dto.response;

public record LoginResponse(
        String refreshToken,
        String accessToken
) {
}
