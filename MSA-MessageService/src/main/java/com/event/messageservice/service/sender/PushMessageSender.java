package com.event.messageservice.service.sender;

import com.event.messageservice.dto.MessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushMessageSender implements MessageSender {

    @Override
    public void sendMessage(MessageRequestDto messageRequestDto) {
        log.info("PushMessage sendMessage called with: {}", messageRequestDto);
        log.info("PUSH 메시지 전송 완료: {}", messageRequestDto);
    }
}
