package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPossessorException extends RuntimeException{
    public InvalidPossessorException() {

    }

    public InvalidPossessorException(String message) {
        super(message);
    }
}
