package org.example.scheduleapi.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.ScheduleDetailResponseDto;
import org.example.scheduleapi.dto.ScheduleRequestDto;
import org.example.scheduleapi.dto.ScheduleResponseDto;
import org.example.scheduleapi.entity.Schedule;
import org.example.scheduleapi.error.GlobalErrorCode;
import org.example.scheduleapi.error.ScheduleErrorCode;
import org.example.scheduleapi.error.exception.RestApiException;
import org.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.data.domain.Sort;
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
        validate(requestDto);

        Schedule savedSchedule = scheduleRepository.save(requestDto.toEntity());
        return new ScheduleResponseDto(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedules(String author) {
        List<Schedule> scheduleList;

        if (author == null) {
            scheduleList = scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
        } else {
            scheduleList = scheduleRepository.findAllByAuthor(author, Sort.by(Sort.Direction.DESC, "updatedAt"));
        }

        List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            scheduleResponseDtoList.add(new ScheduleResponseDto(schedule));
        }

        return scheduleResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDetailResponseDto findScheduleById(Long id) {
        return new ScheduleDetailResponseDto(scheduleRepository.findById(id).orElseThrow(() -> new RestApiException(GlobalErrorCode.RESOURCE_NOT_FOUND)));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String author, String password) {

        if (title == null || author == null || password == null) {
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        }

        if (title.length() > 30)
            throw new RestApiException(ScheduleErrorCode.TITLE_LENGTH_LIMITS);

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RestApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        validatePassword(schedule, password);

        schedule.update(title, author);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id, String password) {
        if (password == null) {
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        }

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RestApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        validatePassword(schedule, password);

        scheduleRepository.delete(schedule);
    }

    private void validate(ScheduleRequestDto requestDto) {
        if (requestDto.getTitle() == null || requestDto.getTitle().isBlank())
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        if (requestDto.getContents() == null || requestDto.getContents().isBlank())
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        if (requestDto.getAuthor() == null || requestDto.getAuthor().isBlank())
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        if (requestDto.getAuthor() == null || requestDto.getAuthor().isBlank())
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        if (requestDto.getPassword() == null || requestDto.getPassword().isBlank())
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);

        if (requestDto.getTitle().length() > 30)
            throw new RestApiException(ScheduleErrorCode.TITLE_LENGTH_LIMITS);

        if (requestDto.getContents().length() > 200)
            throw new RestApiException(ScheduleErrorCode.CONTENTS_LENGTH_LIMITS);
    }

    private void validateId(Long id) {
        if (id == null || id < 1)
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
    }

    private void validatePassword(Schedule schedule, String password) {
        if (!password.equals(schedule.getPassword())) {
            throw new RestApiException(GlobalErrorCode.PASSWORD_MISMATCH);
        }
    }
}
