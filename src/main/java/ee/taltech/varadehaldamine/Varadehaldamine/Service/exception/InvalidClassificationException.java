package ee.taltech.varadehaldamine.Varadehaldamine.Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidClassificationException extends RuntimeException {

    public InvalidClassificationException() {
    }

    public InvalidClassificationException(String message) {
        super(message);
    }
}
