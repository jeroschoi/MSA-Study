package com.event.memberservice.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("예외 발생 시뮬레이션 테스트")
class ExceptionSimulationTest {

    @Test
    @DisplayName("MemberException 생성 - 에러 코드만")
    void createMemberException_OnlyErrorCode() {
        // When & Then
        assertThatThrownBy(() -> {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        })
                .isInstanceOf(MemberException.class)
                .hasMessage("존재하지 않는 사용자입니다.")
                .matches(ex -> ((MemberException) ex).getErrorCode() == MemberErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("MemberException 생성 - 커스텀 메시지")
    void createMemberException_WithCustomMessage() {
        // Given
        String customMessage = "회원 ID 999는 존재하지 않습니다.";

        // When & Then
        assertThatThrownBy(() -> {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND, customMessage);
        })
                .isInstanceOf(MemberException.class)
                .hasMessage(customMessage)
                .matches(ex -> ((MemberException) ex).getErrorCode() == MemberErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("MemberException 생성 - 원인 예외 포함")
    void createMemberException_WithCause() {
        // Given
        RuntimeException cause = new RuntimeException("데이터베이스 연결 실패");

        // When & Then
        assertThatThrownBy(() -> {
            throw new MemberException(MemberErrorCode.DATABASE_ERROR, cause);
        })
                .isInstanceOf(MemberException.class)
                .hasCause(cause)
                .matches(ex -> ((MemberException) ex).getErrorCode() == MemberErrorCode.DATABASE_ERROR);
    }
}
