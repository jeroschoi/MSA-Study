package com.event.memberservice.example.controller;

import com.event.memberservice.example.dto.SampleMemberDto;
import com.event.memberservice.example.service.ExampleMemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/msa/member/v1")
public class ExampleMemberController {

    final private ExampleMemberService exampleService;

    @GetMapping("/info")
    @Operation(summary = "Example API", description = "Example API  to Select Sample Data")
    public ResponseEntity<SampleMemberDto> sampleMemberSelect(Long id) {
        log.info("sampleSelect API Start");
        return ResponseEntity.ok(exampleService.sampleSelect(id));
    }

    @Operation(summary = "Example API", description = "Example API to update Sample example")
    @PostMapping("/info")
    public ResponseEntity<String> sampleMemberUpdate(@RequestBody SampleMemberDto sampleDto){
        log.info("sampleUpdate API Start");
        return ResponseEntity.ok(
                exampleService.sampleUpdate(
                        SampleMemberDto.builder()
                                 .id(sampleDto.getId())
                                 .content(sampleDto.getContent())
                                 .build()
                ));
    }

    @Operation(summary = "Example API", description = "Example API to Insert Sample example")
    @PostMapping("/sample")
    public ResponseEntity<String> sampleMemberInsert(@RequestParam String content){
        log.info("sampleInsert API Start");
        return ResponseEntity.ok(
                exampleService.sampleInsert(
                        SampleMemberDto.builder()
                                .content(content)
                                .build()
                ));
    }

    @PostMapping("/sample")
    @Operation(summary = "Example API", description = "Example API to Delete Sample Data")
    public ResponseEntity<String> sampleMemberDelete(Long id) {
        log.info("sampleMemberDelete API Start");
        return ResponseEntity.ok(exampleService.sampleDelete(id));
    }
}
