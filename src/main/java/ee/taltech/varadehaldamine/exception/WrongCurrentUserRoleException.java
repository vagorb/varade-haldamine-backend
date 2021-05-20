package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongCurrentUserRoleException extends RuntimeException {
    public WrongCurrentUserRoleException(String message) {
        super(message);
    }
}
