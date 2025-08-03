package com.event.messageservice.exception;

import com.event.messageservice.dto.GlobalErrorReponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorReponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder msgBuilder = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                msgBuilder.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );

        return buildErrorResponse(GlobalErrorCode.VALIDATION_ERROR, msgBuilder.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalErrorReponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(GlobalErrorCode.INVALID_INPUT, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GlobalErrorReponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildErrorResponse(GlobalErrorCode.RESOURCE_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<GlobalErrorReponseDto> handleDataAccessException(DataAccessException ex) {
        return buildErrorResponse(GlobalErrorCode.DATABASE_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorReponseDto> handleGenericException(Exception ex) {
        log.error("예기치 못한 오류 발생", ex);
        return buildErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR, "예기치 못한 오류가 발생했습니다.");
    }

    private ResponseEntity<GlobalErrorReponseDto> buildErrorResponse(GlobalErrorCode errorCode, String message) {
        GlobalErrorReponseDto dto = GlobalErrorReponseDto.builder()
                .errorCode(errorCode.getCode())
                .message(message)
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(dto);
    }
}
