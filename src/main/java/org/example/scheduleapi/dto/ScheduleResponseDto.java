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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.author = schedule.getAuthor();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
