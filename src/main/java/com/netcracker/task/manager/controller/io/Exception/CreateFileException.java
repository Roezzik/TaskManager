package com.netcracker.task.manager.controller.io.Exception;

public class CreateFileException extends Exception {

    private String errorMessage;

    public CreateFileException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
