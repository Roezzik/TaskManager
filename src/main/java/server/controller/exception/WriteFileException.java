package server.controller.exception;


/**
 * WriteFileException  - error in writing files
 */
public class WriteFileException extends Exception {
    
    public WriteFileException() {
    }
    
    public WriteFileException(String message) {
        super(message);
    }
}
