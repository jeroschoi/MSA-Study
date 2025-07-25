package com.event.messageservice.example.mapper;


import com.event.messageservice.example.dto.ExampleMessageRequestDto;
import com.event.messageservice.example.dto.ExampleMessageResponseDto;
import com.event.messageservice.example.entity.ExampleMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExampleMessageMapper {

    ExampleMessageMapper INSTANCE = Mappers.getMapper(ExampleMessageMapper.class);

     ExampleMessageRequestDto exampleMessageEntityToDto(ExampleMessageEntity entity);
     ExampleMessageEntity exampleMessageDtoToEntity(ExampleMessageResponseDto dto);
}
