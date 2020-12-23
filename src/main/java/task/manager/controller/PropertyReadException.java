package task.manager.controller;

public class PropertyReadException extends Exception {

    private String errorMessage;

    public PropertyReadException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getPropertyReadException() {
        return errorMessage;
    }
}
