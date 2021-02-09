package server.controller.exception;


/**
 * NotificationException - class error in the task launch thread
 */
public class NotificationException extends Exception {
    
    public NotificationException() {
    }
    
    public NotificationException(String message) {
        super(message);
    }
}
