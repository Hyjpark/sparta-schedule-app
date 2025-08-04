package org.example.scheduleapi.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {
    TITLE_LENGTH_LIMITS(HttpStatus.BAD_REQUEST, "제목은 30자 이내로 제한됩니다."),
    CONTENTS_LENGTH_LIMITS(HttpStatus.BAD_REQUEST, "내용은 200자 이내로 제한됩니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
