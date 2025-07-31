package com.event.messageservice.service;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.exception.MessageErrorCode;
import com.event.messageservice.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageSenderAdapter {

    private final List<MessageSender> messages;

    public MessageSender getMessageSender(MessageRequestDto dto) {
        for (MessageSender sender : messages) {
            if(sender.support(dto.getMessageType())){
                return sender;
            }
        }
        throw new MessageException(MessageErrorCode.UNSUPPORTED_MESSAGE_TYPE);
    }
}
