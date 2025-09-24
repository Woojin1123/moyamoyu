package com.moyamoyu.dto.response;

import com.moyamoyu.entity.Moim;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "모임 정보 응답 DTO")
public record SimpleMoimResponse(
        @Schema(description = "모임 ID")
        Long moimId,
        @Schema(description = "모임 이름")
        String name,
        @Schema(description = "모임 설명")
        String description,
        @Schema(description = "모임 정원")
        Long capacity,
        @Schema(description = "모임 현재 인원")
        Long memberCount,
        @Schema(description = "카테고리")
        String category
) {
    public static SimpleMoimResponse from(Moim moim){
        return new SimpleMoimResponse(
                moim.getId(),
                moim.getName(),
                moim.getDescription(),
                moim.getGrade().getCapacity(),
                moim.getMemberCount(),
                moim.getCategory().name()
        );
    }
}
