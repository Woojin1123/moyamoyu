package com.moyamoyu.controller;

import com.moyamoyu.dto.ApiResponse;
import com.moyamoyu.dto.request.MoimCreateRequest;
import com.moyamoyu.dto.response.SimpleMoimResponse;
import com.moyamoyu.service.MoimService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moims")
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

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SimpleMoimResponse>>> findMoims(
            @RequestParam(defaultValue = "0") int page
    ){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "모임 조회 성공",
                        moimService.findMoims(page)
                )
        );
    }
}
