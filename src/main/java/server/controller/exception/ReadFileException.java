package server.controller.exception;


/**
 * ReadFileException - error in reading files
 */
public class ReadFileException extends Exception {
    
    public ReadFileException() {
    }
    
    public ReadFileException(String message) {
        super(message);
    }
}
