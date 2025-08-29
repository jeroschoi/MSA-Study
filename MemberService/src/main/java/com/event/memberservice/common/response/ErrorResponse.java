package com.event.memberservice.common.response;

import com.event.memberservice.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final boolean success = false;
    private final String code;
    private final String message;
    private final String detail;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ErrorResponse(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static ErrorResponse of(ErrorCode errorCode, String detail) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), detail);
    }
}
