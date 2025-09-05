package com.moyamoyu.dto.response;

import lombok.Builder;

@Builder
public record SimpleMoimResponse(
        Long moimId,
        String name,
        String description,
        Long capacity,
        Long memberCount
) {
}
