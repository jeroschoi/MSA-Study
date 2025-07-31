package com.event.memberservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberCommonResponse<T> {

    @Builder.Default
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
    )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder.Default
    private boolean success = true;

    private String message;
    private T data;
    private String errorCode;
    
    // 성공 응답 생성
    public static <T> MemberCommonResponse<T> success(T data) {
        return MemberCommonResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }
    
    // 실패 응답 생성
    public static <T> MemberCommonResponse<T> error(String message) {
        return MemberCommonResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
    
    public static <T> MemberCommonResponse<T> error(String message, String errorCode) {
        return MemberCommonResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    public static <T> MemberCommonResponse<T> success(String message, T data) {
        return MemberCommonResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 성공 응답 생성 (메시지만, 데이터 없음)
     */
    public static MemberCommonResponse<Void> successWithMessage(String message) {
        return MemberCommonResponse.<Void>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }
} 