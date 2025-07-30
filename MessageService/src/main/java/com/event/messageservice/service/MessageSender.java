package com.event.messageservice.service;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.entity.MessageType;
import org.springframework.stereotype.Component;

@Component
public interface MessageSender {

    boolean suport(MessageType messageType);

    void sendMessage(MessageRequestDto messageRequestDto);
}
