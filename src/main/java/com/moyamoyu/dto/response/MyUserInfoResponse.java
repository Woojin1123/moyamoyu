package com.moyamoyu.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyUserInfoResponse(
        String email,
        String nickname,
        LocalDateTime createdAt,
        String fullAddress,
        String introduce,
        String profileImg
) {
}
