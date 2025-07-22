package com.event.memberservice.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class SampleMessageResponseDto {

    private Long memberId;
    private String returnCode;
    private String returnMessage;
}
