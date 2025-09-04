package com.event.messageservice.controller;

import com.event.messageservice.dto.GlobalReponseDto;
import com.event.messageservice.dto.MessageRequestDto;
import com.event.messageservice.dto.MultiMessageResponseDto;
import com.event.messageservice.entity.MessageHistory;
import com.event.messageservice.service.MessageSendService;
import com.event.messageservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/msa/v1/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageSendService messageSendService;

    @PostMapping("/send/single")
    @Operation(summary = "Send Single Message", description = "단건 메시지 전송")
    public ResponseEntity<GlobalReponseDto> sendSingleMessage(@RequestBody @Valid MessageRequestDto requestDto ) {
        messageSendService.sendMessage(requestDto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/send/multi")
    @Operation(summary = "Send Multiple Messages", description = "다건 메시지 전송")
    public ResponseEntity<MultiMessageResponseDto> sendMultipleMessages(@RequestBody @Valid List<MessageRequestDto> requestDtoList) {

        int sentCount = 0;
        List<MessageRequestDto> failedList = new ArrayList<>();

        for (MessageRequestDto dto : requestDtoList) {
            try {
                messageSendService.sendMessage(dto);
                sentCount++;
            } catch (Exception e) {
                log.error("메세지 전송 실패 - {}", dto, e);
                failedList.add(dto);
            }
        }

        MultiMessageResponseDto response = MultiMessageResponseDto.builder()
                .totalCount(requestDtoList.size())
                .sentCount(sentCount)
                .failedList(failedList)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create Message History", description = "메시지 이력 생성")
    public ResponseEntity<GlobalReponseDto> createMessageHistory(@RequestBody @Valid MessageRequestDto dto) {
        MessageHistory messageHistory = messageService.saveMessageHistory(dto);
        GlobalReponseDto responseDto = new GlobalReponseDto();
        responseDto.setData(messageHistory);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "Get Message History", description = "회원 ID 로 메시지 이력 조회")
    public ResponseEntity<GlobalReponseDto> getMessagetHitsroyMemberId(@PathVariable String memberId) {
        List<MessageHistory> messageHistoryList = messageService.getMessageMemberId(memberId);
        GlobalReponseDto responseDto = new GlobalReponseDto();
        responseDto.setData(messageHistoryList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/phone/{phoneNumber}")
    @Operation(summary = "Get Message History by Phone Number", description = "휴대폰 번호로 메시지 이력 조회")
    public ResponseEntity<GlobalReponseDto> getMessageHistoryPhoneNumber(@PathVariable String phoneNumber) {
        List<MessageHistory> messageHistoryList = messageService.getMessagePhoneNumber(phoneNumber);
        GlobalReponseDto responseDto = new GlobalReponseDto();
        responseDto.setData(messageHistoryList);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{memberId}")
    @Operation(summary = "Update Message History", description = "회원 ID 로 메시지 이력 비활성화")
    public ResponseEntity<GlobalReponseDto> updateMessageHistory(@PathVariable String memberId) {
        int updateSize = messageService.visibleFalseMessageHistory(memberId);
        GlobalReponseDto responseDto = new GlobalReponseDto();
        responseDto.setData(new HashMap<String, Integer>() {{;
            put("updateSize", updateSize);
        }});
        return ResponseEntity.ok().body(responseDto);
    }
}
