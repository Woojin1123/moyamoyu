package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모임 참가 수락/거절 요청 DTO")
public record ProcessJoinRequest(
        @Schema(description = "수락/거절")
        String status,
        @Schema(description = "거절 이유 메세지")
        String rejectReason
) {
}
