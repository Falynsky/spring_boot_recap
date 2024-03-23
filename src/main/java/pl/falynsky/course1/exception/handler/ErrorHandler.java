package pl.falynsky.course1.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.falynsky.course1.exception.CustomException;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e, WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, webRequest);
    }
}
