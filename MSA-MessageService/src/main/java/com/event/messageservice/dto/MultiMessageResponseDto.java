package com.event.messageservice.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record MultiMessageResponseDto(int totalCount,
                                      int sentCount,
                                      List<MessageRequestDto> failedList) {
}
