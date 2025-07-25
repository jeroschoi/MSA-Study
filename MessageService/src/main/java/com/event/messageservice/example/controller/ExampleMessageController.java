package com.event.messageservice.example.controller;

import com.event.messageservice.example.dto.ExampleMessageRequestDto;
import com.event.messageservice.example.dto.ExampleMessageResponseDto;
import com.event.messageservice.example.service.ExampleMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/msa/message/v1")
public class ExampleMessageController {

    final private ExampleMessageService exampleMessageService;

    @GetMapping("/info")
    @Operation(summary = "Example API", description = "Example API  to Select Sample Data")
    public ResponseEntity<ExampleMessageResponseDto> sampleMemberSelect(Long id) {
        log.info("sampleSelect API Start");
        return ResponseEntity.ok(ExampleMessageResponseDto.builder().build());
    }

    @Operation(summary = "Example API", description = "Example API to update Sample example")
    @PostMapping("/info")
    public ResponseEntity<String> sampleMemberUpdate(@RequestBody ExampleMessageRequestDto exampleMessageRequestDto){
        log.info("sampleUpdate API Start");
        return ResponseEntity.ok("test");
    }

    @Operation(summary = "Example API", description = "Example API to Insert Sample example")
    @PostMapping("/register")
    public ResponseEntity<String> sampleMemberInsert(@RequestParam String content){
        log.info("sampleInsert API Start");
        return ResponseEntity.ok("test");
    }

    @PostMapping("/remove")
    @Operation(summary = "Example API", description = "Example API to Delete Sample Data")
    public ResponseEntity<String> sampleMemberDelete(Long id) {
        log.info("sampleMemberDelete API Start");
        return ResponseEntity.ok("test");
    }
}
