package com.event.memberservice.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final String errorCode;
    private final String message;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}