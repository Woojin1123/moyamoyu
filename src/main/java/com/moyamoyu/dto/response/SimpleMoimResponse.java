package com.moyamoyu.dto.response;

import com.moyamoyu.entity.Moim;
import lombok.Builder;

@Builder
public record SimpleMoimResponse(
        Long moimId,
        String name,
        String description,
        Long capacity,
        Long memberCount
) {
    public static SimpleMoimResponse from(Moim moim){
        return new SimpleMoimResponse(
                moim.getId(),
                moim.getName(),
                moim.getDescription(),
                moim.getGrade().getCapacity(),
                moim.getMemberCount()
        );
    }
}
