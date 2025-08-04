package org.example.scheduleapi.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
