package org.example.scheduleapi.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.scheduleapi.entity.Schedule;

@Getter
public class ScheduleRequestDto {

    private String title;
    private String contents;
    private String author;
    private String password;

    public Schedule toEntity() {
        return Schedule.builder()
                .title(title)
                .contents(contents)
                .author(author)
                .password(password)
                .build();
    }

}
