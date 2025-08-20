package com.event.memberservice.common.exception;

import com.event.memberservice.common.response.ErrorResponse;
import com.event.memberservice.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 모든 비즈니스 예외를 처리합니다. (MemberException 등 BusinessException의 모든 자식 클래스)
     */
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(MemberException e) {
        log.warn("BusinessException occurred: code={}, message='{}'", e.getErrorCode(), e.getMessage(), e);

        // ErrorCode의 메시지와 Exception의 메시지가 다를 경우, Exception의 메시지를 detail로 사용
        String detail = e.getMessage().equals(e.getMessage()) ? null : e.getMessage();
        ErrorResponse response = ErrorResponse.of(e.getErrorCode(), detail);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    /**
     * WebClient 관련 예외를 처리합니다.
     */
    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<ErrorResponse> handleWebClientException(WebClientException e) {
        ErrorCode errorCode = CommonErrorCode.WEBCLIENT_ERROR;
        log.error("WebClientException occurred: message='{}'", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(errorCode, e.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    /**
     * @Valid를 통한 유효성 검사 실패 시 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorCode errorCode = CommonErrorCode.VALIDATION_ERROR;
        String detail = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("'%s': %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        log.warn("Validation failed: {}", detail);
        ErrorResponse response = ErrorResponse.of(errorCode, detail);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    /**
     * 위에서 처리하지 못한 모든 예외를 처리합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        log.error("Unhandled Exception occurred: message='{}'", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}