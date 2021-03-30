package ee.taltech.varadehaldamine.Varadehaldamine.Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidWorthException extends RuntimeException {

    public InvalidWorthException() {
    }

    public InvalidWorthException(String message) {
        super(message);
    }
}
