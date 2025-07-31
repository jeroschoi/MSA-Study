package com.event.memberservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 에러 응답을 위한 DTO 클래스
 * 모든 예외 상황에 대해 일관된 응답 형식을 제공합니다.
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * 에러 코드 (예: M001, M002, V001)
     */
    private String code;

    /**
     * 에러 메시지 (사용자에게 보여줄 메시지)
     */
    private String message;

    /**
     * 에러 상세 정보 (추가적인 설명, null 가능)
     */
    private String detail;

    /**
     * 에러 발생 시각
     * JSON 응답에서 "yyyy-MM-dd HH:mm:ss" 형식으로 출력됩니다.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
