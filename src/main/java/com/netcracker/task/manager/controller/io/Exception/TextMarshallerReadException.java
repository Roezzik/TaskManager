package com.netcracker.task.manager.controller.io.Exception;

public class TextMarshallerReadException extends Exception {

    private String errorMessage;

    public TextMarshallerReadException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
