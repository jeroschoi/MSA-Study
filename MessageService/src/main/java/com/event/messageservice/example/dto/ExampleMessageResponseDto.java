package com.event.messageservice.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class ExampleMessageResponseDto {

    private Long memberId;
    private String returnCode;
    private String returnMessage;
}
