package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.request.UserDeleteRequest;
import com.moyamoyu.dto.request.UserInfoPatchRequest;
import com.moyamoyu.dto.response.MyUserInfoResponse;
import com.moyamoyu.dto.response.UserSimpleResponse;
import com.moyamoyu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
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
