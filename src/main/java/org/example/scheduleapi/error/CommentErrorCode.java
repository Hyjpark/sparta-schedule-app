package org.example.scheduleapi.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    CONTENTS_LENGTH_LIMITS(HttpStatus.BAD_REQUEST, "내용은 100자 이내로 제한됩니다."),
    COMMENT_COUNT_EXCEEDED(HttpStatus.BAD_REQUEST, "댓글 개수는 10개까지는 허용됩니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
