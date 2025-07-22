package com.event.memberservice.example.service;

import com.event.memberservice.example.dto.SampleMemberDto;
import com.event.memberservice.example.dto.SampleMessageRequestDto;
import com.event.memberservice.example.dto.SampleMessageResponseDto;
import com.event.memberservice.example.entity.SampleMemberEntity;
import com.event.memberservice.example.mapper.ExampleMemberMapper;
import com.event.memberservice.example.repository.SampleMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleMemberService {

    final private SampleMemberRepository sampleRepository;

    public SampleMemberDto sampleSelect(Long id) {
        return ExampleMemberMapper.INSTANCE.sampleEntityToDto(sampleRepository.findById(id).orElse(null));
    }

    @Transactional
    public String sampleUpdate(SampleMemberDto sampleDto) {
        log.info("sampleUpdate data : {}", sampleDto.toString());
        sampleRepository.save(ExampleMemberMapper.INSTANCE.sampleDtoToEntity(sampleDto));
        return "update success";
    }

    @Transactional
    public String sampleInsert(SampleMemberDto sampleDto) {
        log.info("sampleInsert data : {}", sampleDto.toString());
        sampleRepository.save(SampleMemberEntity.builder()
                .content(sampleDto.getContent())
                .build());
        return "insert success";
    }

    @Transactional
    public String sampleDelete(Long id) {
        sampleRepository.deleteById(id);
         SampleMessageResponseDto responseMono = WebClient.builder()
                .baseUrl("http://localhost:8080/example")
                .defaultHeader("Content-Type", "application/json")
                .build().post()
                .bodyValue(
                        SampleMessageRequestDto.builder()
                        .memberId(1L))
                .retrieve()
                .bodyToMono(SampleMessageResponseDto.class)
                .block();

        log.info(String.valueOf(responseMono));

        return "200".equals(responseMono.getReturnCode())?"delete success but message not Delete":"delete success";
    }
}
