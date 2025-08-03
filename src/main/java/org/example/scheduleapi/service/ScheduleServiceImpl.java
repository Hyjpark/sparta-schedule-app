package org.example.scheduleapi.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.ScheduleRequestDto;
import org.example.scheduleapi.dto.ScheduleResponseDto;
import org.example.scheduleapi.entity.Schedule;
import org.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements SchedulesService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        Schedule savedSchedule = scheduleRepository.save(requestDto.toEntity());
        return new ScheduleResponseDto(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedules(String author) {
        List<Schedule> scheduleList = new ArrayList<>();

        if (author == null) {
            scheduleList = scheduleRepository.findAll();
        } else {
            scheduleList = scheduleRepository.findAllByAuthor(author);
        }

        List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            scheduleResponseDtoList.add(new ScheduleResponseDto(schedule));
        }

        return scheduleResponseDtoList;
    }
}
