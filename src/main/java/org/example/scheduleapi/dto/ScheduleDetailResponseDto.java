package org.example.scheduleapi.dto;

import lombok.Getter;
import org.example.scheduleapi.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleDetailResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CommentResponseDto> comments;

    public ScheduleDetailResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.author = schedule.getAuthor();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.comments = schedule.getComments().stream()
                .map(comment -> CommentResponseDto.builder()
                        .comment(comment).build())
                .collect(Collectors.toList());
    }
}
