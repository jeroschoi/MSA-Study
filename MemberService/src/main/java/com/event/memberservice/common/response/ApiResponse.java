package com.event.memberservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON으로 만들지 않음
public class ApiResponse<T> {
    private final boolean success = true;
    private final T data;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    // 성공 (데이터만 있을 때)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    // 성공 (데이터와 메시지가 함께 있을 때)
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
}