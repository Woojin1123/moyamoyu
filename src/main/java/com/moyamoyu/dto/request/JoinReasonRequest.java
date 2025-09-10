package com.moyamoyu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모임 참가 요청 DTO")
public record JoinReasonRequest(
        @Schema(description = "모임 참가 메세지")
        String message
) {
}
