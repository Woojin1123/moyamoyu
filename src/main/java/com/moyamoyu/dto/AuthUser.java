package com.moyamoyu.dto;

import com.moyamoyu.entity.enums.ServiceRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Security 인증 객체")
public class AuthUser {
    @Schema(description = "인증된 유저 ID")
    private Long id;
    @Schema(description = "인증된 유저 이메일")
    private String email;
    @Schema(description = "인증된 유저 역할")
    private ServiceRole role;
}
