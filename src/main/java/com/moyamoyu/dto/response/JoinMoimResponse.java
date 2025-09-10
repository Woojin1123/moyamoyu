package com.moyamoyu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모임 참가 신청 응답 DTO")
public record JoinMoimResponse(
        @Schema(description = "참여할 모임의 ID", example = "1")
        Long moimId,
        @Schema(description = "모임 참여 상태" , allowableValues = {"PENDING","APPROVED","REJECTED"})
        String status
) {
}
