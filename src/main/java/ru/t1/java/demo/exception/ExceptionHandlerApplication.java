package ru.t1.java.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerApplication {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleResponseStatusException(EntityNotFoundException ex) {
        return generateErrorResponse(ex, NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return generateErrorResponse(ex, BAD_REQUEST);
    }

    private ResponseEntity<String> generateErrorResponse(Exception ex, HttpStatus httpStatus){
        return new ResponseEntity<>(ex.getMessage(), httpStatus);
    }
}
