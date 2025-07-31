package com.event.memberservice.exception;

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
    @DisplayName("ErrorResponse Builder 패턴 테스트")
    void errorResponse_BuilderPattern() {
        // Given
        String code = "M001";
        String message = "테스트 메시지";
        String detail = "상세 정보";
        LocalDateTime timestamp = LocalDateTime.now();

        // When
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(code)
                .message(message)
                .detail(detail)
                .timestamp(timestamp)
                .build();

        // Then
        assertThat(errorResponse.getCode()).isEqualTo(code);
        assertThat(errorResponse.getMessage()).isEqualTo(message);
        assertThat(errorResponse.getDetail()).isEqualTo(detail);
        assertThat(errorResponse.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    @DisplayName("ErrorResponse JSON 직렬화 테스트")
    void errorResponse_JsonSerialization() throws Exception {
        // Given
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("M007")
                .message("존재하지 않는 사용자입니다.")
                .detail("사용자 ID 999를 찾을 수 없습니다.")
                .timestamp(LocalDateTime.of(2025, 7, 31, 21, 0, 0))
                .build();

        // When
        String json = objectMapper.writeValueAsString(errorResponse);

        // Then
        assertThat(json).contains("\"code\":\"M007\"");
        assertThat(json).contains("\"message\":\"존재하지 않는 사용자입니다.\"");
        assertThat(json).contains("\"detail\":\"사용자 ID 999를 찾을 수 없습니다.\"");
        assertThat(json).contains("\"timestamp\":\"2025-07-31 21:00:00\"");
    }

    @Test
    @DisplayName("ErrorResponse JSON 역직렬화 테스트")
    void errorResponse_JsonDeserialization() throws Exception {
        // Given
        String json = """
                {
                    "code": "M002",
                    "message": "이미 사용 중인 이메일입니다.",
                    "detail": "test@example.com은 이미 가입된 이메일입니다.",
                    "timestamp": "2025-07-31 21:00:00"
                }
                """;

        // When
        ErrorResponse errorResponse = objectMapper.readValue(json, ErrorResponse.class);

        // Then
        assertThat(errorResponse.getCode()).isEqualTo("M002");
        assertThat(errorResponse.getMessage()).isEqualTo("이미 사용 중인 이메일입니다.");
        assertThat(errorResponse.getDetail()).isEqualTo("test@example.com은 이미 가입된 이메일입니다.");
        assertThat(errorResponse.getTimestamp()).isEqualTo(LocalDateTime.of(2025, 7, 31, 21, 0, 0));
    }

    @Test
    @DisplayName("ErrorResponse - detail이 null인 경우")
    void errorResponse_WithNullDetail() throws Exception {
        // Given
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("M007")
                .message("존재하지 않는 사용자입니다.")
                .detail(null)
                .timestamp(LocalDateTime.now())
                .build();

        // When
        String json = objectMapper.writeValueAsString(errorResponse);

        // Then
        assertThat(json).contains("\"detail\":null");
        assertThat(errorResponse.getDetail()).isNull();
    }
}
