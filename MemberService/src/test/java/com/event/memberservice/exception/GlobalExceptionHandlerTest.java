package com.event.memberservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("GlobalExceptionHandler 테스트")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("MemberException 처리 - 기본 메시지")
    void handleMemberException_WithDefaultMessage() {
        // Given
        MemberException exception = new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMemberException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("M007");
        assertThat(response.getBody().getMessage()).isEqualTo("존재하지 않는 사용자입니다.");
        assertThat(response.getBody().getDetail()).isNull();
        assertThat(response.getBody().getTimestamp()).isBefore(LocalDateTime.now().plusSeconds(1));
    }

    @Test
    @DisplayName("MemberException 처리 - 커스텀 메시지")
    void handleMemberException_WithCustomMessage() {
        // Given
        String customMessage = "사용자 ID 999를 찾을 수 없습니다.";
        MemberException exception = new MemberException(MemberErrorCode.MEMBER_NOT_FOUND, customMessage);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMemberException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("M007");
        assertThat(response.getBody().getMessage()).isEqualTo("존재하지 않는 사용자입니다.");
        assertThat(response.getBody().getDetail()).isEqualTo(customMessage);
    }

    @Test
    @DisplayName("중복 이메일 예외 처리 - BAD_REQUEST 상태")
    void handleMemberException_DuplicateEmail() {
        // Given
        MemberException exception = new MemberException(MemberErrorCode.DUPLICATE_EMAIL);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMemberException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getCode()).isEqualTo("M002");
        assertThat(response.getBody().getMessage()).isEqualTo("이미 사용 중인 이메일입니다.");
    }

    @Test
    @DisplayName("WebClient 요청 예외 처리")
    void handleWebClientException_RequestException() {
        // Given
        WebClientRequestException exception = mock(WebClientRequestException.class);
        when(exception.getMessage()).thenReturn("Connection refused");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleWebClientException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("M011");
        assertThat(response.getBody().getMessage()).isEqualTo("메시지 전송 중 오류가 발생했습니다.");
        assertThat(response.getBody().getDetail()).isEqualTo("메시지 서비스 연결에 실패했습니다.");
    }

    @Test
    @DisplayName("일반 예외 처리 - INTERNAL_SERVER_ERROR")
    void handleGenericException() {
        // Given
        RuntimeException exception = new RuntimeException("예상치 못한 오류");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("M012");
        assertThat(response.getBody().getMessage()).isEqualTo("내부 서버 오류가 발생했습니다.");
        assertThat(response.getBody().getDetail()).isEqualTo("예상치 못한 서버 오류가 발생했습니다.");
    }

    @Test
    @DisplayName("Validation 예외 처리")
    void handleValidationException() {
        // Given
        BindException exception = new BindException("target", "objectName");
        FieldError fieldError = new FieldError("objectName", "userId", "사용자 ID는 필수입니다.");
        exception.addError(fieldError);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("V001");
        assertThat(response.getBody().getMessage()).isEqualTo("입력값 검증에 실패했습니다.");
        assertThat(response.getBody().getDetail()).contains("userId: 사용자 ID는 필수입니다.");
    }
}
