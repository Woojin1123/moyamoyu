package com.moyamoyu.entity;

import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;

import java.util.Arrays;

public enum JoinRequestStatus {
    PENDING, APPROVED, REJECTED, CANCELED;

    public static JoinRequestStatus ofIgnoreCase(String status) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(
                        () -> new ApiException(ErrorCode.BAD_REQUEST, "잘못된 ENUM 입니다.")
                );

    }
}
