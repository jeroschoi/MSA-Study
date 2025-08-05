package com.event.messageservice.dto;

import lombok.Builder;


@Builder
public record GlobalErrorReponseDto(String errorCode, String message) {
}
