package com.moyamoyu.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyUserInfoResponse(
        String email,
        String nickname,
        LocalDateTime createdAt,
        String roadAddress,
        String detailAddress,
        String introduce,
        String profileImg
) {
}
