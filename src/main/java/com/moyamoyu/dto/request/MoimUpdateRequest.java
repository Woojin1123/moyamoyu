package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모임 정보 수정 요청 DTO")
public record MoimUpdateRequest(
        @Schema(description = "모임 이름")
        String name,
        @Schema(description = "모임 설명")
        String description,
        @Schema(description = "모임 참가 정책", allowableValues = {"OPEN","PRIVATE"})
        String joinPolicy
) {
}
