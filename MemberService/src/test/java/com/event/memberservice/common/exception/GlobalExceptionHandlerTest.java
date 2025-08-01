package com.event.memberservice.common.exception;

import com.event.memberservice.common.response.ErrorResponse;
import com.event.memberservice.member.exception.MemberErrorCode;
import com.event.memberservice.member.exception.MemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GlobalExceptionHandler 테스트")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("BusinessException 처리")
    void handleBusinessException() {
        // Given
        MemberException exception = new MemberException(MemberErrorCode.MEMBER_NOT_FOUND, "Custom detail message");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBusinessException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ErrorResponse errorBody = response.getBody();
        assertThat(errorBody).isNotNull();
        assertThat(errorBody.getCode()).isEqualTo(MemberErrorCode.MEMBER_NOT_FOUND.getCode());
        assertThat(errorBody.getMessage()).isEqualTo(MemberErrorCode.MEMBER_NOT_FOUND.getMessage());
        assertThat(errorBody.getDetail()).isEqualTo("Custom detail message");
    }

    @Test
    @DisplayName("WebClient 요청 예외 처리")
    void handleWebClientException() {
        // Given
        // headers 인자에 null 대신 새로운 HttpHeaders 객체를 전달합니다.
        WebClientRequestException exception = new WebClientRequestException(
                new RuntimeException("Connection refused"), HttpMethod.POST, URI.create("http://test"), new HttpHeaders());

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleWebClientException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        ErrorResponse errorBody = response.getBody();
        assertThat(errorBody).isNotNull();
        assertThat(errorBody.getCode()).isEqualTo(CommonErrorCode.WEBCLIENT_ERROR.getCode());
    }

    @Test
    @DisplayName("일반 예외 처리")
    void handleException() {
        // Given
        RuntimeException exception = new RuntimeException("Unexpected error");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        ErrorResponse errorBody = response.getBody();
        assertThat(errorBody).isNotNull();
        assertThat(errorBody.getCode()).isEqualTo(CommonErrorCode.INTERNAL_SERVER_ERROR.getCode());
    }
}
