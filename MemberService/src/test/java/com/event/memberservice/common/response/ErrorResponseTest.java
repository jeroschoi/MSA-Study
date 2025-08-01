package com.event.memberservice.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ErrorResponse DTO 테스트")
class ErrorResponseTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("ErrorResponse 정적 팩토리 메서드 테스트")
    void errorResponse_of() {
        // Given
        // 테스트를 위해 임시 ErrorCode 구현
        enum TestErrorCode implements com.event.memberservice.common.exception.ErrorCode {
            TEST_ERROR(org.springframework.http.HttpStatus.BAD_REQUEST, "T001", "테스트 에러");
            private final org.springframework.http.HttpStatus httpStatus;
            private final String code;
            private final String message;
            TestErrorCode(org.springframework.http.HttpStatus httpStatus, String code, String message) {
                this.httpStatus = httpStatus;
                this.code = code;
                this.message = message;
            }
            public org.springframework.http.HttpStatus getHttpStatus() { return httpStatus; }
            public String getCode() { return code; }
            public String getMessage() { return message; }
        }

        // When
        ErrorResponse response = ErrorResponse.of(TestErrorCode.TEST_ERROR, "상세 정보");

        // Then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getCode()).isEqualTo("T001");
        assertThat(response.getMessage()).isEqualTo("테스트 에러");
        assertThat(response.getDetail()).isEqualTo("상세 정보");
    }

    @Test
    @DisplayName("ErrorResponse JSON 직렬화 테스트")
    void errorResponse_JsonSerialization() throws Exception {
        // Given
        // 테스트를 위해 임시 ErrorCode 구현
        enum TestErrorCode implements com.event.memberservice.common.exception.ErrorCode {
            NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "M007", "존재하지 않는 사용자입니다.");
            // ... (위와 동일한 구현)
            private final org.springframework.http.HttpStatus httpStatus;
            private final String code;
            private final String message;
            TestErrorCode(org.springframework.http.HttpStatus httpStatus, String code, String message) {
                this.httpStatus = httpStatus;
                this.code = code;
                this.message = message;
            }
            public org.springframework.http.HttpStatus getHttpStatus() { return httpStatus; }
            public String getCode() { return code; }
            public String getMessage() { return message; }
        }
        ErrorResponse errorResponse = ErrorResponse.of(TestErrorCode.NOT_FOUND, "사용자 ID 999를 찾을 수 없습니다.");

        // When
        String json = objectMapper.writeValueAsString(errorResponse);

        // Then
        assertThat(json).contains("\"success\":false");
        assertThat(json).contains("\"code\":\"M007\"");
        assertThat(json).contains("\"message\":\"존재하지 않는 사용자입니다.\"");
        assertThat(json).contains("\"detail\":\"사용자 ID 999를 찾을 수 없습니다.\"");
    }
}

