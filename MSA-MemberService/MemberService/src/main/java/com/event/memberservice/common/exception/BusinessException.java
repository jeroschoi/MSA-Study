package com.event.memberservice.common.exception;

import lombok.Getter;

/**
 * 서비스의 모든 비즈니스 예외(MemberException 등)가 상속받을 부모 클래스입니다.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 상세 메시지를 직접 지정하고 싶을 때 사용
    public BusinessException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

}
