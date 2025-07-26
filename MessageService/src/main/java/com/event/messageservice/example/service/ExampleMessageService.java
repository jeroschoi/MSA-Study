package com.event.messageservice.example.service;

import com.event.messageservice.example.dto.ExampleMessageRequestDto;
import com.event.messageservice.example.dto.ExampleMessageResponseDto;
import com.event.messageservice.example.mapper.ExampleMessageMapper;
import com.event.messageservice.example.repository.ExampleMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleMessageService {

    final private ExampleMessageRepository exampleMessageRepository;

    public ExampleMessageResponseDto exampleMessageSelect(Long id) {
            ExampleMessageMapper.INSTANCE.exampleMessageEntityToDto(exampleMessageRepository.findById(id).orElse(null));
        return null;
    }

    @Transactional
    public String exampleMessageUpdate(ExampleMessageRequestDto exampleMessageRequestDto) {
        log.info("sampleUpdate data : {}", exampleMessageRequestDto.toString());
        return "update success";
    }

    @Transactional
    public String exampleMessageInsert(ExampleMessageRequestDto exampleMessageRequestDto) {
        log.info("sampleInsert data : {}", exampleMessageRequestDto.toString());
        return "insert success";
    }

    @Transactional
    public ExampleMessageResponseDto exampleMessageDelete(Long memberId) {
        exampleMessageRepository.deleteById(memberId);
        // TODO Return 어떻게 설계 하는게 좋을까?
        return ExampleMessageResponseDto.builder()
                .memberId(memberId)
                .returnMessage("delete success")
                .returnCode("S200")
                .build();
    }
}
