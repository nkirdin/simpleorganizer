package simpleorganizer.exception;

import javax.ejb.ApplicationException;

/**
 * 
 * @author user
 * The DataDuplicatedException is used for indicating duplicated data in fields with unique = true flag. 
 */
@ApplicationException(rollback = true)
public class DataDuplicatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataDuplicatedException() {}

    public DataDuplicatedException(String message){
        super(message);
    }

    public DataDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataDuplicatedException(Throwable cause) {
        super(cause);
    }

}
