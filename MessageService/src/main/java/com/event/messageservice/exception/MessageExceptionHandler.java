package com.event.messageservice.exception;

import com.event.messageservice.dto.GlobalErrorReponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class MessageExceptionHandler {
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<GlobalErrorReponseDto> handleMessageException(MessageException e) {
        GlobalErrorReponseDto responseDto = GlobalErrorReponseDto.builder()
                .errorCode(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(responseDto);
    }

}
