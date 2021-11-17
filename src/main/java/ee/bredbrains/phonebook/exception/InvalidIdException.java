package ee.bredbrains.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String invalidId) {
        super("Value " + invalidId + " cannot be recognized as ID.");
    }
}
