package com.moyamoyu.dto.request;

public record EmailConfirmRequest(
        String email,
        String token
) {
}
