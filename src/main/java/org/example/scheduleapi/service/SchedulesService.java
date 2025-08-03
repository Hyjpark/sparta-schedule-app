package org.example.scheduleapi.service;

import org.example.scheduleapi.dto.ScheduleRequestDto;
import org.example.scheduleapi.dto.ScheduleResponseDto;

import java.util.List;

public interface SchedulesService {
     ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
     List<ScheduleResponseDto> findAllSchedules(String author);
}
