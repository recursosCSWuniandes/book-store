package co.edu.uniandes.csw.bookbasico.exceptions;

public class BusinessValidationException extends RuntimeException {

    public BusinessValidationException() {
    }

    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(Throwable cause) {
        super(cause);
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
