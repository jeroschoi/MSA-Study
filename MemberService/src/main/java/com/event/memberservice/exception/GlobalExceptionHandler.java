package com.event.memberservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // === 상수 정의 (Space 규칙: 대문자+언더바) ===
    private static final String VALIDATION_ERROR_CODE = "V001";
    private static final String VALIDATION_ERROR_MESSAGE = "입력값 검증에 실패했습니다.";
    private static final String UNEXPECTED_ERROR_MESSAGE = "예상치 못한 서버 오류가 발생했습니다.";

    /**
     * MemberException 처리
     */
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException ex) {
        logMemberException(ex);

        String detail = extractDetailFromException(ex);
        ErrorResponse errorResponse = createErrorResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getMessage(),
                detail
        );

        HttpStatus status = determineHttpStatus(ex.getErrorCode());
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * WebClient 통신 관련 예외 처리
     */
    @ExceptionHandler({WebClientException.class,
            WebClientRequestException.class,
            WebClientResponseException.class})
    public ResponseEntity<ErrorResponse> handleWebClientException(Exception ex) {
        logWebClientException(ex);

        String detail = determineWebClientErrorDetail(ex);
        ErrorResponse errorResponse = createErrorResponse(
                MemberErrorCode.MESSAGE_SEND_ERROR.getCode(),
                MemberErrorCode.MESSAGE_SEND_ERROR.getMessage(),
                detail
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    /**
     * Validation 예외 처리
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        logValidationException(ex);

        String validationErrors = extractValidationErrors(ex);
        ErrorResponse errorResponse = createErrorResponse(
                VALIDATION_ERROR_CODE,
                VALIDATION_ERROR_MESSAGE,
                validationErrors
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 예상치 못한 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logGenericException(ex);

        ErrorResponse errorResponse = createErrorResponse(
                MemberErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                MemberErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                UNEXPECTED_ERROR_MESSAGE
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // === 헬퍼 메서드들 (Space 규칙: 30라인 이하) ===

    private ErrorResponse createErrorResponse(String code, String message, String detail) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .detail(detail)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String extractDetailFromException(MemberException ex) {
        return ex.getMessage().equals(ex.getErrorCode().getMessage())
                ? null
                : ex.getMessage();
    }

    private HttpStatus determineHttpStatus(MemberErrorCode errorCode) {
        switch (errorCode) {
            case MEMBER_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case DUPLICATE_USER_ID:
            case DUPLICATE_EMAIL:
            case DUPLICATE_CONTACT:
            case INVALID_PASSWORD:
            case INVALID_EMAIL:
            case INVALID_CONTACT:
            case ALREADY_EXITED:
                return HttpStatus.BAD_REQUEST;
            case INACTIVE_MEMBER:
                return HttpStatus.FORBIDDEN;
            case DATABASE_ERROR:
            case MESSAGE_SEND_ERROR:
            case INTERNAL_SERVER_ERROR:
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String determineWebClientErrorDetail(Exception ex) {
        if (ex instanceof WebClientRequestException) {
            return "메시지 서비스 연결에 실패했습니다.";
        } else if (ex instanceof WebClientResponseException responseEx) {
            return String.format("메시지 서비스 응답 오류 (상태코드: %d)",
                    responseEx.getRawStatusCode());
        } else {
            return "메시지 서비스 통신 중 오류가 발생했습니다.";
        }
    }

    private String extractValidationErrors(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException validEx) {
            return validEx.getBindingResult().getFieldErrors().stream()
                    .map(error -> String.format("%s: %s",
                            error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.joining(", "));
        } else if (ex instanceof BindException bindEx) {
            return bindEx.getFieldErrors().stream()
                    .map(error -> String.format("%s: %s",
                            error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.joining(", "));
        }
        return "입력값 검증 실패";
    }

    // === 로깅 메서드들 ===

    private void logMemberException(MemberException ex) {
        log.error("Member Exception 발생: code={}, message={}",
                ex.getErrorCode().getCode(), ex.getErrorCode().getMessage(), ex);
    }

    private void logWebClientException(Exception ex) {
        log.error("WebClient Exception 발생: type={}, message={}",
                ex.getClass().getSimpleName(), ex.getMessage(), ex);
    }

    private void logValidationException(Exception ex) {
        log.error("Validation Exception 발생: {}", ex.getMessage(), ex);
    }

    private void logGenericException(Exception ex) {
        log.error("예상치 못한 Exception 발생: type={}, message={}",
                ex.getClass().getSimpleName(), ex.getMessage(), ex);
    }
}
