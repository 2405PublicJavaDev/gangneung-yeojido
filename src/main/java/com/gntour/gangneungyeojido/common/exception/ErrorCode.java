package com.gntour.gangneungyeojido.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE("C001", "Invalid Input Value", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("C002", "Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    SAMPLE("C003", " Sample Error", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_VALUE( "C004", " Invalid Type Value", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(final String code, final String message, final HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
