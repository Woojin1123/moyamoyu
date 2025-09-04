package com.moyamoyu.entity;

import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;

import java.util.Arrays;

public enum ServiceRole {
    ROLE_USER, ROLE_ADMIN;

    public static ServiceRole of(String role){
        return Arrays.stream(ServiceRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(
                        () -> new ApiException(ErrorCode.BAD_REQUEST)
                );
    }
}
