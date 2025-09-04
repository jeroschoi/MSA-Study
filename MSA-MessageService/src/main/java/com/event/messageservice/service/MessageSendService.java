package com.event.messageservice.service;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSendService {

    private final MessageSenderFactory messageSenderFactory;
    private final MessageService messageSendService;

    /**
     * 메시지 저장 및 전송
     */
    @Transactional
    public void sendMessage(MessageRequestDto dto) {
        log.info("메시지 전송 요청 - {}", dto);
        MessageSender messageSender = messageSenderFactory.createMessageSender(dto.getMessageType());

        log.info("adapter.getMessageSender(dto) - {}", messageSender);
        messageSendService.saveMessageHistory(dto);
        messageSender.sendMessage(dto);

        log.info("메시지 전송 성공 - {}", dto);
    }
}
