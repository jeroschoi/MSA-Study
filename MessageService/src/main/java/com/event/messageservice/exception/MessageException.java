package com.event.messageservice.exception;

public class MessageException extends RuntimeException{
    private final MessageErrorCode errorCode;

    public MessageException(MessageErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MessageErrorCode getErrorCode() {
        return errorCode;
    }
}
