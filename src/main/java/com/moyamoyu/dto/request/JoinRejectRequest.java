package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모임 참가 거절 요청 DTO")
public record JoinRejectRequest(
        @Schema(description = "거절 이유 메세지")
        String rejectReason
) {
}
