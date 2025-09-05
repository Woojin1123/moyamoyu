package com.moyamoyu.dto;

import com.moyamoyu.entity.enums.ServiceRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthUser {
    private Long id;
    private String email;
    private ServiceRole role;
}
