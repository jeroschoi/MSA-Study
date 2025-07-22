package com.event.memberservice.example.mapper;


import com.event.memberservice.example.dto.SampleMemberDto;
import com.event.memberservice.example.entity.SampleMemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExampleMemberMapper {

    ExampleMemberMapper INSTANCE = Mappers.getMapper(ExampleMemberMapper.class);

     SampleMemberDto sampleEntityToDto(SampleMemberEntity entity);
     SampleMemberEntity sampleDtoToEntity(SampleMemberDto dto);
}
