package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OngoingInventoryDoesNotExist extends RuntimeException {

    public OngoingInventoryDoesNotExist(String message) {
        super(message);
    }
}
