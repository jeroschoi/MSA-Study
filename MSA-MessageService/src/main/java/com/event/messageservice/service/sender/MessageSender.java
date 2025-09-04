package com.event.messageservice.service.sender;

import com.event.messageservice.dto.MessageRequestDto;
import org.springframework.stereotype.Component;

@Component
public interface MessageSender {

    void sendMessage(MessageRequestDto messageRequestDto);
}
