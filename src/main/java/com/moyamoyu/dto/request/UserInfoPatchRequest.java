package com.moyamoyu.dto.request;

import jakarta.validation.constraints.Size;

public record UserInfoPatchRequest(
        String nickname,
        String roadAddress,
        String detailAddress,
        String zipcode,
        @Size(max = 200 , message = "최대 200자까지 입력 가능합니다.")
        String introduce
) {
}
