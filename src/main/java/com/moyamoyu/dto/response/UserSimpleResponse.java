package com.moyamoyu.dto.response;

import lombok.Builder;

@Builder
public record UserSimpleResponse(
        String nickname,
        String introduce
) {
}
