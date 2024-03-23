package pl.falynsky.course1.exception;

public class CustomException extends  RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Custom exception message";

    public CustomException() {
        super(EXCEPTION_MESSAGE);
    }
}
