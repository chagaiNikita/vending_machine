package exceptions;

public class InvalidCardAccessException extends Exception {
    public InvalidCardAccessException(String message) {
        super(message);
    }
    public InvalidCardAccessException() {

    }
}
