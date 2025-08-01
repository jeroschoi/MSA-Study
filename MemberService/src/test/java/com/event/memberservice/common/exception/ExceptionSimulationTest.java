package com.event.memberservice.common.exception;

import com.event.memberservice.member.exception.MemberErrorCode;
import com.event.memberservice.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
                .hasMessage(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
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
    @DisplayName("BusinessException 생성 (CommonErrorCode 사용)")
    void createBusinessException_WithCommonCode() {
        // When & Then
        assertThatThrownBy(() -> {
            throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        })
                .isInstanceOf(BusinessException.class)
                .matches(ex -> ((BusinessException) ex).getErrorCode() == CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
