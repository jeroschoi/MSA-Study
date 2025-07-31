package com.event.memberservice.dto;

import com.event.memberservice.repository.entity.MessageType;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class MessageRequest {

    private final String userId;
    private final String email;
    private final String contact;
    private final MessageType messageType;
}