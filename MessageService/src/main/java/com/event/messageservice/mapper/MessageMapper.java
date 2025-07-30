package com.event.messageservice.mapper;


import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.entity.MessageHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageHistory messageDtoToEntity(MessageRequestDto dto);
}
