package org.example.scheduleapi.dto;

import lombok.Getter;
import org.example.scheduleapi.entity.Comment;
import org.example.scheduleapi.entity.Schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String author;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.author = schedule.getAuthor();
        this.created_at = schedule.getCreatedAt();
        this.updated_at = schedule.getUpdatedAt();
    }
}
