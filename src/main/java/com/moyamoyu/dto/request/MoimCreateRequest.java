package com.moyamoyu.dto.request;

public record MoimCreateRequest(
        String name,
        String description,
        String joinPolicy,
        String category,
        Long leaderId
) {
}
