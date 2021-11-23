package ee.bredbrains.phonebook.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserConflictException extends RuntimeException {
    public UserConflictException(String param, String value) {
        super("User with " + param + ", that contains value " + value + " cannot be created.");
    }
}
