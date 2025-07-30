package com.event.messageservice.exception;

public enum MessageErrorCode {

    MESSAGE_NOT_FOUND("Message not found");

    private final String message;

    MessageErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
