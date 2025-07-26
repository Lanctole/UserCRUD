package lanctole.controller;

import lanctole.exception.EmailAlreadyExistsException;
import lanctole.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(UserServiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserServiceException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException  ex) {
        return ex.getMessage();
    }
}
