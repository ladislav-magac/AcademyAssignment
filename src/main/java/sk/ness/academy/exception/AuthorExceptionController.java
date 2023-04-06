package sk.ness.academy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorExceptionController {
    @ExceptionHandler(value = AuthorsNotFoundException.class)
    public ResponseEntity<Object> exception(AuthorsNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}