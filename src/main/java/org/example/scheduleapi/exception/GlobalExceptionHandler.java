package org.example.scheduleapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> handlePasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(makeErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    private ErrorResponse makeErrorResponse(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .code(status)
                .message(message)
                .build();
    }
}
