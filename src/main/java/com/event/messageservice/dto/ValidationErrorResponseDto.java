package com.event.messageservice.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record ValidationErrorResponseDto(String code, String message, Map<String,String> fieldErrors) {
}
