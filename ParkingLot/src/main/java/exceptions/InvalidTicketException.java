package exceptions;

public class InvalidTicketException extends RuntimeException {
    public InvalidTicketException(String errorMsg) {
        super(errorMsg);
    }
}