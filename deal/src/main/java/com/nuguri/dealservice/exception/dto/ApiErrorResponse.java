package com.nuguri.dealservice.exception.dto;

public class ApiErrorResponse {
    private String errorCode;

    private ApiErrorResponse() {
    }

    public ApiErrorResponse(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
