package com.moyamoyu.dto;

import com.moyamoyu.exception.ApiException;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String message;
    private T data;

    public ApiResponse(String message, T data ){
        this.message = message;
        this.data = data;
    }
    public ApiResponse(String message){
        this.message = message;
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }
    public static <T> ApiResponse<T> failure(ApiException e){
        return new ApiResponse<>(e.getMessage());
    }
}
