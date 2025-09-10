package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모임 생성 요청 DTO")
public record MoimCreateRequest(
        @Schema(description = "모임 이름")
        String name,
        @Schema(description = "모임 설명")
        String description,
        @Schema(description = "모임 참가 정책" , allowableValues = {"OPEN","PRIVATE"})
        String joinPolicy,
        @Schema(description = "모임 카테고리", allowableValues = {"STUDY","SPORTS","HOBBY"})
        String category,
        @Schema(description = "모임 생성 유저 ID")
        Long leaderId
) {
}
