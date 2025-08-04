package org.example.scheduleapi.dto;

import lombok.Getter;
import org.example.scheduleapi.entity.Comment;
import org.example.scheduleapi.entity.Schedule;

@Getter
public class CommentRequestDto {

    private Long scheduleId;
    private String contents;
    private String author;
    private String password;

    public Comment toEntity(Schedule schedule) {
        return Comment.builder()
                .schedule(schedule)
                .contents(contents)
                .author(author)
                .password(password)
                .build();
    }

}
