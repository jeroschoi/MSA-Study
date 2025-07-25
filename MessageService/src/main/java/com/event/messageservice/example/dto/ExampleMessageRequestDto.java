package com.event.messageservice.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class ExampleMessageRequestDto {
    private Long messageId;
    private Long memberId;
}
