package lanctole.validation;

import lanctole.exception.UserServiceException;
import lanctole.model.User;

public class UserValidator {
    public static void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new UserServiceException("User name must not be blank");
        }
        if (user.getName().length() < 3 || user.getName().length() > 50) {
            throw new UserServiceException("User name must be between 3 and 50 characters");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new UserServiceException("Email must not be blank");
        }
        if (user.getEmail().length() < 3 || user.getEmail().length() > 50) {
            throw new UserServiceException("User email must be between 3 and 50 characters");
        }
        if (user.getAge() < 0) {
            throw new UserServiceException("Age must not be negative");
        }
    }
}
