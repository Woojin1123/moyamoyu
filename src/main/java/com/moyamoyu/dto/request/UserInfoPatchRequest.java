package com.moyamoyu.dto.request;

public record UserInfoPatchRequest(

        String nickname,
        String roadAddress,
        String detailAddress,
        String zipcode,
        String introduce
) {
}
