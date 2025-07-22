package com.event.memberservice.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class SampleMessageRequestDto {
    private Long messageId;
    private Long memberId;
}
