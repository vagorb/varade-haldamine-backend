package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InventoryExcelException extends RuntimeException{
    public InventoryExcelException() {

    }

    public InventoryExcelException(String message) {
        super(message);
    }
}
