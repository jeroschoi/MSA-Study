package com.event.memberservice.member.exception;

import com.event.memberservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "해당 회원을 찾을 수 없습니다."),
    DUPLICATE_USER_ID(HttpStatus.CONFLICT, "M002", "이미 사용 중인 사용자 ID입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "M003", "이미 사용 중인 이메일입니다."),
    DUPLICATE_CONTACT(HttpStatus.CONFLICT, "M004", "이미 사용 중인 연락처입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "M005", "비밀번호가 일치하지 않습니다."),
    ALREADY_EXITED(HttpStatus.BAD_REQUEST, "M006", "이미 탈퇴한 회원입니다."),
    INACTIVE_MEMBER(HttpStatus.FORBIDDEN, "M007", "비활성화된 회원입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}