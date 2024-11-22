package exception;

public class DaoException extends RuntimeException {
    public DaoException(String cause) {
        super(cause);
    }
}
