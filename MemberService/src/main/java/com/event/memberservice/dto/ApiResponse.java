package com.event.memberservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 성공 응답을 위한 공통 DTO 클래스
 * 모든 성공적인 API 응답에 일관된 형식을 제공합니다.
 *
 * @param <T> 실제 데이터의 타입 (MemberResponse, List<MemberResponse> 등)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * 요청 성공 여부 (항상 true)
     */
    private boolean success;

    /**
     * 성공 메시지
     */
    private String message;

    /**
     * 실제 응답 데이터
     */
    private T data;

    /**
     * 응답 생성 시각
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // === 정적 팩토리 메서드들 ===

    /**
     * 기본 성공 응답 생성
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("성공")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 커스텀 메시지와 함께 성공 응답 생성
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 데이터 없이 성공 메시지만 반환
     */
    public static ApiResponse<Void> successWithMessage(String message) {
        return ApiResponse.<Void>builder()
                .success(true)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
