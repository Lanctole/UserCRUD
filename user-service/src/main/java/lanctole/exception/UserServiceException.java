package lanctole.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Long id) {
        super("User with id " + id + " not found");
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
