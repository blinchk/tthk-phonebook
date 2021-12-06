package ee.bredbrains.phonebook.exception.contact;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MalformedDuplicateException extends RuntimeException {
}
