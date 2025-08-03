package com.event.messageservice.exception;

import org.springframework.http.HttpStatus;

public enum MessageErrorCode {

    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "M002", "휴대폰 번호가 유효하지 않습니다."),
    UNSUPPORTED_MESSAGE_TYPE(HttpStatus.BAD_REQUEST, "M003", "지원하지 않는 메세지 타입입니다."),
    MESSAGE_HISTORY_NOT_FOUND_BY_MEMBER(HttpStatus.NOT_FOUND, "M004", "메세지 이력을 찾을 수 없습니다. 회원 ID로 조회"),
    MESSAGE_HISTORY_NOT_FOUND_BY_PHONE(HttpStatus.NOT_FOUND, "M005", "메세지 이력을 찾을 수 없습니다. 휴대폰 번호로 조회");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MessageErrorCode(HttpStatus status, String code, String message) {
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
