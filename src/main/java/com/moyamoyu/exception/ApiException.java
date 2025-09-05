package com.moyamoyu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
    public ApiException(ErrorCode errorCode,String customMessage) {
        super(customMessage);
        this.httpStatus = errorCode.getHttpStatus();
    }
}
