package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAssetException extends RuntimeException {

    public InvalidAssetException() {

    }

    public InvalidAssetException(String message) {
        super(message);
    }
}
