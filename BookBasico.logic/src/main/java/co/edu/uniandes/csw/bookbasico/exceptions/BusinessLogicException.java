package co.edu.uniandes.csw.bookbasico.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BusinessLogicException extends Exception {

    public BusinessLogicException() {
        super();
    }

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(Throwable cause) {
        super(cause);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

}
