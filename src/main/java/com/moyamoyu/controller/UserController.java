package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.request.UserDeleteRequest;
import com.moyamoyu.dto.request.UserInfoPatchRequest;
import com.moyamoyu.dto.response.MyUserInfoResponse;
import com.moyamoyu.dto.response.UserSimpleResponse;
import com.moyamoyu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController" , description = "유저 정보 관련 API")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "인증된 사용자에 대한 정보를 조회하는 API")
    public ResponseEntity<ApiResponse<MyUserInfoResponse>> getMyInfo(
            @AuthenticationPrincipal AuthUser authUser
    ){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "내 정보 조회 성공",
                        userService.getMyInfo(authUser)
                )
        );
    }

    @GetMapping("/{userId}")
    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회하는 API")
    public ResponseEntity<ApiResponse<UserSimpleResponse>> getUserInfo(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "유저 정보 조회 성공",
                        userService.getUserInfo(userId)
                )
        );
    }

    @PatchMapping("/me")
    @Operation(summary = "내 정보 수정", description = "인증된 사용자에 대한 정보를 수정하는 API")
    public ResponseEntity<ApiResponse<MyUserInfoResponse>> patchUserInfo(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UserInfoPatchRequest userInfoPatchRequest
    ){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "내 정보 수정 성공",
                        userService.patchUserInfo(authUser, userInfoPatchRequest)
                )
        );
    }

    @DeleteMapping("/me")
    @Operation(summary = "회원탈퇴", description = "인증된 사용자에 대한 회원탈퇴 API")
    public ResponseEntity<ApiResponse<Long>> deleteUser(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UserDeleteRequest userDeleteRequest
    ){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "회원 탈퇴 성공",
                        userService.deleteUser(authUser,userDeleteRequest)
                )
        );
    }
}
