package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.request.JoinReasonRequest;
import com.moyamoyu.dto.request.JoinRejectRequest;
import com.moyamoyu.dto.request.MoimCreateRequest;
import com.moyamoyu.dto.request.MoimUpdateRequest;
import com.moyamoyu.dto.response.JoinMoimResponse;
import com.moyamoyu.dto.response.SimpleJoinRequest;
import com.moyamoyu.dto.response.SimpleMoimResponse;
import com.moyamoyu.exception.ApiException;
import com.moyamoyu.exception.ErrorCode;
import com.moyamoyu.service.MoimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moims")
@Tag(name = "MoimController", description = "모임 관련 API")
public class MoimController {
    private final MoimService moimService;

    @PostMapping
    @Operation(summary = "모임 생성", description = "모임 생성 API")
    public ResponseEntity<ApiResponse<SimpleMoimResponse>> createMoim(
            @RequestBody MoimCreateRequest moimCreateRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 생성 성공",
                        moimService.createMoim(moimCreateRequest)
                ));
    }

    @GetMapping
    @Operation(summary = "모임 전체 조회", description = "전체 모임 조회 API")
    public ResponseEntity<ApiResponse<Page<SimpleMoimResponse>>> findMoims(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "STUDY") String category
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "전체 모임 조회 성공",
                        moimService.findMoims(page, category)
                )
        );
    }

    @GetMapping
    @Operation(summary = "가입한 모임 조회", description = "가입한 모임 조회 API")
    public ResponseEntity<ApiResponse<Page<SimpleMoimResponse>>> findJoinedMoims(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "true") Boolean joined
    ) {
        if (joined) throw new ApiException(ErrorCode.BAD_REQUEST);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "가입한 모임 조회 성공",
                        moimService.findJoinedMoims(page, authUser)
                )
        );
    }

    @DeleteMapping("/{moimId}")
    @Operation(summary = "모임 삭제", description = "모임 삭제 API")
    public ResponseEntity<ApiResponse<Long>> deleteMoim(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long moimId
    ) {
        moimService.deleteMoim(authUser, moimId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 삭제 성공",
                        moimService.deleteMoim(authUser, moimId)
                ));
    }

    @PatchMapping("/{moimId}")
    @Operation(summary = "모임 정보 수정", description = "모임 정보 수정 API")
    public ResponseEntity<ApiResponse<SimpleMoimResponse>> updateMoim(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long moimId,
            @RequestBody MoimUpdateRequest moimUpdateRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 수정 성공",
                        moimService.updateMoim(authUser, moimId, moimUpdateRequest)
                )
        );
    }

    @PostMapping("/{moimId}/join")
    @Operation(summary = "모임 참가 신청", description = "모임 참가 신청 API")
    public ResponseEntity<ApiResponse<JoinMoimResponse>> joinMoim(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long moimId,
            @RequestBody JoinReasonRequest joinReasonRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 참가 요청 성공",
                        moimService.joinMoim(authUser, moimId, joinReasonRequest)
                )
        );
    }

    @PostMapping("/{moimId}/join/{requestId}/approve")
    @Operation(summary = "참가 신청 수락", description = "모임 참가 요청에 대해 수락하는 API")
    public ResponseEntity<ApiResponse<Long>> approveJoinMoim(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long moimId,
            @PathVariable Long requestId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 참가 승인",
                        moimService.approveJoinMoim(authUser, moimId, requestId)
                )
        );
    }

    @PostMapping("/{moimId}/join/{requestId}/reject")
    @Operation(summary = "참가 신청 거절", description = "모임 참가 요청에 대해 거절하는 API")
    public ResponseEntity<ApiResponse<Long>> rejectJoinMoim(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long moimId,
            @PathVariable Long requestId,
            @RequestBody JoinRejectRequest joinRejectRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 참가 거절",
                        moimService.rejectJoinMoim(authUser, moimId, requestId, joinRejectRequest.rejectReason())
                )
        );
    }

    @GetMapping("/{moimId}/join")
    @Operation(summary = "참가 요청 조회", description = "모임에 대한 참가 요청을 전부 조회하는 API")
    public ResponseEntity<ApiResponse<Page<SimpleJoinRequest>>> findAllJoinRequest(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long moimId,
            @RequestParam(name = "status", defaultValue = "PENDING") String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "page", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 참가 요청 조회 성공",
                        moimService.findAllJoinRequest(authUser, moimId, status, page, size)
                )
        );
    }
}
