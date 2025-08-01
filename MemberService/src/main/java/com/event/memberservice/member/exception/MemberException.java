package com.event.memberservice.member.exception;

import com.event.memberservice.common.exception.BusinessException;
import com.event.memberservice.common.exception.ErrorCode;

public class MemberException extends BusinessException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
