package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.AuthUser;
import com.moyamoyu.dto.response.SimpleJoinRequestResponse;
import com.moyamoyu.service.JoinRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-requests")
@Tag(name = "JoinRequestController", description = "참가 요청 관련 API")
public class JoinRequestController {
    private final JoinRequestService joinRequestService;
    @GetMapping("/received")
    public ResponseEntity<ApiResponse<Page<SimpleJoinRequestResponse>>> findAllReceivedRequest(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "6") int size
    ){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "요청 조회 성공",
                        joinRequestService.findAllReceivedRequest(page,size,authUser.getId())
                )
        );
    }
}
