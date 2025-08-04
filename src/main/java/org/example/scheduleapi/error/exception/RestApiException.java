package org.example.scheduleapi.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ErrorCode errorCode;
}
