package com.moyamoyu.dto.request;

public record MoimUpdateRequest(
        String name,
        String description,
        String joinPolicy
) {
}
