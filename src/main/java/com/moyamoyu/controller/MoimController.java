package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.request.MoimCreateRequest;
import com.moyamoyu.dto.response.SimpleMoimResponse;
import com.moyamoyu.service.MoimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moim")
public class MoimController {
    private final MoimService moimService;

    @PostMapping
    public ResponseEntity<ApiResponse<SimpleMoimResponse>> createMoim(
            @RequestBody MoimCreateRequest moimCreateRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 생성 성공",
                        moimService.createMoim(moimCreateRequest)
          ));
    }
}
