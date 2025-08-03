package org.example.scheduleapi.dto;

import lombok.Getter;
import org.example.scheduleapi.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private Long scheduleId;
    private String contents;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.contents = comment.getContents();
        this.author = comment.getAuthor();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}
