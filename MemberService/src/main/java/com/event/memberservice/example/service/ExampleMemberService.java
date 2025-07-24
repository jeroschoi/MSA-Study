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
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleMemberService {

    final private SampleMemberRepository sampleRepository;

    public SampleMemberDto sampleMemberSelect(Long id) {
        return ExampleMemberMapper.INSTANCE.sampleEntityToDto(sampleRepository.findById(id).orElse(null));
    }

    @Transactional
    public String sampleMemberUpdate(SampleMemberDto sampleDto) {
        log.info("sampleUpdate data : {}", sampleDto.toString());
        sampleRepository.save(ExampleMemberMapper.INSTANCE.sampleDtoToEntity(sampleDto));
        return "update success";
    }

    @Transactional
    public String sampleMemberInsert(SampleMemberDto sampleDto) {
        log.info("sampleInsert data : {}", sampleDto.toString());
        sampleRepository.save(SampleMemberEntity.builder()
                .content(sampleDto.getContent())
                .build());
        return "insert success";
    }

    @Transactional
    public String sampleMemberDelete(Long id) {
        sampleRepository.deleteById(id);
        SampleMessageResponseDto responseMono = WebClient.builder()
            .baseUrl("http://localhost:8080/msa/v1/sample")
            .defaultHeader("Content-Type", "application/json")
            .build().post()
            .bodyValue(
                    SampleMessageRequestDto.builder()
                    .memberId(1L).build())
            .retrieve()
            .bodyToMono(SampleMessageResponseDto.class)
            .doOnSuccess(sampleMessageResponseDto -> log.info("Response: {}", sampleMessageResponseDto.toString()))
            .doOnError(WebClientResponseException.class , ex -> log.error("Error Code check : {} , {}", ex.getMessage() , ex.getStatusCode()))
            //.doOnError(throwable -> log.error("Error occurred: {}", throwable.getMessage()))
            .block();

        log.info(String.valueOf(responseMono));

        // TODO Return 어떻게 설계 하는게 좋을까?
        return "200".equals(responseMono.getReturnCode())?"delete success but message not Delete":"delete success";
    }
}
