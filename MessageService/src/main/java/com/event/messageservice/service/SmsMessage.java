package com.event.messageservice.service;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.entity.MessageType;
import org.springframework.stereotype.Component;

@Component
public class SmsMessage implements MessageSender {

    @Override
    public boolean suport(MessageType type) {
        return type == MessageType.SMS;
    }

    @Override
    public void sendMessage(MessageRequestDto messageRequestDto) {

    }
}
