package ee.bredbrains.phonebook.exception.permission;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionException extends RuntimeException {
    public PermissionException() {
        super("Action Not Allowed");
    }

    public PermissionException(String message) {
        super(message);
    }
}
