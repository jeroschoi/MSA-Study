package com.event.messageservice;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.dto.MessageType;
import com.event.messageservice.service.MessageSendService;
import com.event.messageservice.service.sender.MessageSender;
import com.event.messageservice.service.MessageSenderFactory;
import com.event.messageservice.service.MessageService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageSendServiceTest {

    @Mock
    MessageService messageService; // 메시지 이력 저장 Mock 추가

    @Mock
    MessageSenderFactory messageSenderFactory;

    @Mock
    MessageSender messageSender;

    @InjectMocks
    MessageSendService messageSendService;

    @Test
    @DisplayName("메시지 전송 성공 테스트")
    void sendMessage_success() {
        // given
        MessageRequestDto dto = MessageRequestDto.builder()
                .messageType(MessageType.PUSH)
                .memberId("memberId")
                .content("Test message content")
                .phoneNumber("010-1234-5678")
                .build();

        when(messageSenderFactory.createMessageSender(dto.getMessageType()))
                .thenReturn(messageSender);

        messageSendService.sendMessage(dto);

        // 검증
        verify(messageSender).sendMessage(dto);
    }
}
