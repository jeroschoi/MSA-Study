package com.event.messageservice.service;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.entity.MessageType;
import org.springframework.stereotype.Component;

@Component
public class KakaoMessage implements MessageSender {
    @Override
    public boolean support(MessageType type) {
        return type == MessageType.KAKAO;
    }

    @Override
    public void sendMessage(MessageRequestDto messageRequestDto) {

    }
}
