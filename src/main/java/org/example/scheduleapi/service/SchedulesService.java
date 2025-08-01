package org.example.scheduleapi.service;

import org.example.scheduleapi.dto.ScheduleRequestDto;
import org.example.scheduleapi.dto.ScheduleResponseDto;

public interface SchedulesService {
     ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
}
