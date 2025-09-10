package com.moyamoyu.dto.response;

import com.moyamoyu.entity.JoinRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Schema(description = "모임 참가 요청 조회 응답 DTO")
public record SimpleJoinRequest(
        @Schema(description = "모임 ID" , example = "1")
        Long id,
        @Schema(description = "참가 요청 생성 일자")
        LocalDateTime createdAt,
        @Schema(description = "참가 요청 메세지")
        String message,
        @Schema(description = "참가 요청 상태")
        JoinRequestStatus joinRequestStatus
) {
}
