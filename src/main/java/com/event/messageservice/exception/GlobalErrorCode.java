package com.event.messageservice.exception;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode {
    // 400 BAD REQUEST
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "G001", "잘못된 요청입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "G002", "요청값 검증에 실패했습니다."),

    // 404 NOT FOUND
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "G404", "요청한 자원을 찾을 수 없습니다."),

    // 409 CONFLICT
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "G409", "이미 존재하는 자원입니다."),


    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G501", "데이터베이스 오류가 발생했습니다."),
    REDIS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G502", "Redis 처리 중 오류가 발생했습니다."),
    KAFKA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G503", "Kafka 처리 중 오류가 발생했습니다."),

    // 504
    TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "G504", "서비스 응답 시간이 초과되었습니다."),
    EXTERNAL_SERVICE_ERROR(HttpStatus.BAD_GATEWAY, "G502", "외부 서비스와의 통신에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    GlobalErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
