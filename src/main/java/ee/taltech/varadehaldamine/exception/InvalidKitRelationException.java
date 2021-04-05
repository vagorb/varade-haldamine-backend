package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidKitRelationException extends RuntimeException {

    public InvalidKitRelationException() {

    }

    public InvalidKitRelationException(String message) {
        super(message);
    }
}
