package com.moyamoyu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode { // 에러 코드관리를 위한 Enum
    // EXAMPLE_ERROR("EXAMPLE", "USER NOT FOUND.", HttpStatus.NOT_FOUND)
    // BadRequest 400
    BAD_REQUEST("BAD_REQUEST", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "인증에 필요한 토큰을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    // UnAuthorized 401
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "인증이 만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    // Forbidden 403
    ACCESS_DENIED("ACCESS_DENIED", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    // NotFound 404
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "RESOURCE IS NOT FOUND", HttpStatus.NOT_FOUND),
    // Internal Server Error 500
    API_REQUEST_FAILED("API_REQUEST_FAILED", "API 요청이 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}