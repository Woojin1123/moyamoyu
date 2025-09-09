package com.moyamoyu.dto.response;

import com.moyamoyu.entity.JoinRequestStatus;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record SimpleJoinRequest(
        Long id,
        LocalDateTime createdAt,
        String message,
        JoinRequestStatus joinRequestStatus
) {
}
