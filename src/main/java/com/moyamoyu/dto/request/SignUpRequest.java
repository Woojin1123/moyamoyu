package com.moyamoyu.dto.request;

public record SignUpRequest(
        String email,
        String password,
        String nickname,
        String role,
        String address
) {
}
