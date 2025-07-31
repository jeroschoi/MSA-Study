package com.event.messageservice.mapper;

import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.entity.MessageHistory;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T20:31:01+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 17.0.7 (Oracle Corporation)"
)
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageHistory messageDtoToEntity(MessageRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        MessageHistory.MessageHistoryBuilder messageHistory = MessageHistory.builder();

        messageHistory.memberId( dto.getMemberId() );
        messageHistory.phoneNumber( dto.getPhoneNumber() );
        messageHistory.messageType( dto.getMessageType() );
        messageHistory.content( dto.getContent() );

        return messageHistory.build();
    }
}
