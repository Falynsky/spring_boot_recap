package pl.falynsky.course1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomException extends  RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Custom exception message";

    public CustomException() {
        super(EXCEPTION_MESSAGE);
    }
}
