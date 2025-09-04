package com.event.messageservice;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.dto.MessageType;
import com.event.messageservice.entity.MessageHistory;
import com.event.messageservice.exception.MessageErrorCode;
import com.event.messageservice.exception.MessageException;
import com.event.messageservice.repository.MessageRespository;
import com.event.messageservice.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class) // Mockito 객체를 생성하기 위한 애노테이션
class MessageServiceTest {

    @Mock  // Mock 객체를 생성하기 위한 애노테이션
    MessageRespository repository;

    @InjectMocks // Mock 객체를 주입 받기 위한 애노테이션
    MessageService messageService;

    @Test
    @DisplayName("메시지 이력 저장 성공 테스트")
    void saveMessageHistorySuccessTest() {
        // given
        MessageRequestDto dto = MessageRequestDto.builder()
                .messageType(MessageType.SMS)
                .memberId("memberId")
                .content("Test message content")
                .phoneNumber("010-1234-5678")
                .build();

        MessageHistory messageHistory = MessageHistory.builder()
                .id(1L)
                .memberId(dto.getMemberId())
                .phoneNumber(dto.getPhoneNumber())
                .messageType(dto.getMessageType())
                .content(dto.getContent())
                .sentAt(LocalDateTime.now())
                .visible(true)
                .build();

        // when
        when(repository.save(Mockito.any(MessageHistory.class)))
                .thenReturn(messageHistory);

        // then
        MessageHistory savedEntity = messageService.saveMessageHistory(dto);
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.isVisible()).isTrue();
        assertThat(savedEntity.getMemberId()).isEqualTo(dto.getMemberId());
    }

    @Test
    @DisplayName("휴대폰 번호로 메시지 이력 조회 성공 테스트")
    void getMessagePhoneNumberSuccessTest() {
        String phoneNumber = "010-1234-5678";

        // given
        MessageRequestDto dto = MessageRequestDto.builder()
                .messageType(MessageType.SMS)
                .memberId("memberId")
                .content("Test message content")
                .phoneNumber("010-1234-5678")
                .build();


        MessageHistory savedEntity = MessageHistory.builder()
                .id(1L)
                .memberId(dto.getMemberId())
                .phoneNumber(dto.getPhoneNumber())
                .messageType(dto.getMessageType())
                .content(dto.getContent())
                .sentAt(LocalDateTime.now())
                .visible(true)
                .build();

        // when
        when(repository.findByPhoneNumberAndVisibleTrueOrderBySentAtDesc(phoneNumber))
                .thenReturn(Collections.singletonList(savedEntity));

        List<MessageHistory> messageHistoryList = messageService.getMessagePhoneNumber(phoneNumber);

        // then
        assertThat(messageHistoryList).isNotNull();
    }


    @Test
    @DisplayName("휴대폰 번호로 메시지 이력 조회 실패 테스트")
    void getMessagePhoneNumberFailTest() {
        // given
        String phoneNumber = "010-1234-5678";

        // when
        when(repository.findByPhoneNumberAndVisibleTrueOrderBySentAtDesc(phoneNumber))
                .thenReturn(Collections.emptyList());
        // then
        assertThatThrownBy(() -> {
            messageService.getMessagePhoneNumber(phoneNumber);
        })
                .isInstanceOf(MessageException.class)
                .hasMessage(MessageErrorCode.MESSAGE_HISTORY_NOT_FOUND_BY_PHONE.getMessage());
    }

    @Test
    @DisplayName("회원 ID로 메시지 이력 조회 성공 테스트")
    void getMessageIdSuccessTest() {
        // given
        String memberId = "member123";

        MessageHistory messageHistory = MessageHistory.builder()
                .id(1L)
                .memberId(memberId)
                .phoneNumber("010-1234-5678")
                .messageType(MessageType.SMS)
                .content("Test message content")
                .sentAt(LocalDateTime.now())
                .visible(true)
                .build();

        // when
        when(repository.findByMemberIdAndVisibleTrueOrderBySentAtDesc(memberId))
                .thenReturn(Collections.singletonList(messageHistory));

        List<MessageHistory> result = messageService.getMessageMemberId(memberId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getMemberId()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("회원 ID로 메시지 이력 조회 실패 테스트")
    void getMessageIdFailTest() {
        // given
        String memberId = "member123";

        // when
        when(repository.findByMemberIdAndVisibleTrueOrderBySentAtDesc(memberId))
                .thenReturn(Collections.emptyList());
        // then
        assertThatThrownBy(() -> {
            messageService.getMessageMemberId(memberId);
        })
                .isInstanceOf(MessageException.class)
                .hasMessage(MessageErrorCode.MESSAGE_HISTORY_NOT_FOUND_BY_MEMBER.getMessage());
    }

    @Test
    @DisplayName("회원 탈퇴시 Visible 필드 업데이트 성공 테스트")
    void updateVisibleSuccessTest() {
        // given
        String memberId = "member123";

        MessageHistory messageHistory = MessageHistory.builder()
                .id(1L)
                .memberId(memberId)
                .phoneNumber("010-1234-5678")
                .messageType(MessageType.SMS)
                .content("Test message content")
                .sentAt(LocalDateTime.now())
                .visible(true)
                .build();

        List<MessageHistory> historyList = Collections.singletonList(messageHistory);

        when(repository.findByMemberIdAndVisibleTrueOrderBySentAtDesc(memberId))
                .thenReturn(historyList);

        // when
        int updateSize = messageService.visibleFalseMessageHistory(memberId);

        // then
        assertThat(messageHistory.isVisible()).isFalse();
        assertThat(updateSize).isEqualTo(1);
        verify(repository, Mockito.times(1)).findByMemberIdAndVisibleTrueOrderBySentAtDesc(memberId);
    }

}
