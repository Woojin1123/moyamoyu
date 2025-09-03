package com.moyamoyu.exception;

import com.moyamoyu.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException e) {
        ApiResponse<Object> response = ApiResponse.failure(e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(response);
    }
}