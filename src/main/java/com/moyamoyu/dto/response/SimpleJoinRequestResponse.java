package com.moyamoyu.dto.response;

import com.moyamoyu.entity.JoinRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Schema(description = "모임 참가 요청 조회 응답 DTO")
public record SimpleJoinRequestResponse(
        @Schema(description = "join-request ID")
        Long id,
        @Schema(description = "요청 유저 ID")
        Long participantId,
        @Schema(description = "요청 유저 nickname")
        String nickname,
        @Schema(description = "모임 ID" , example = "1")
        Long moimId,
        @Schema(description = "모임 이름" , example = "1")
        String moimName,
        @Schema(description = "참가 요청 생성 일자")
        LocalDateTime requestedAt,
        @Schema(description = "참가 요청 메세지")
        String message,
        @Schema(description = "참가 요청 상태")
        JoinRequestStatus joinRequestStatus
) {
}
