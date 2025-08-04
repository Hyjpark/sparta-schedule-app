package org.example.scheduleapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.ScheduleDetailResponseDto;
import org.example.scheduleapi.dto.ScheduleRequestDto;
import org.example.scheduleapi.dto.ScheduleResponseDto;
import org.example.scheduleapi.entity.Comment;
import org.example.scheduleapi.entity.Schedule;
import org.example.scheduleapi.exception.PasswordMismatchException;
import org.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements SchedulesService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        if (requestDto.getTitle() == null || requestDto.getContents() == null ||
                requestDto.getAuthor() == null || requestDto.getPassword() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and content cannot be null");

        if (requestDto.getTitle().length() > 30 ||  requestDto.getContents().length() > 200)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are long.");

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
        return new ScheduleDetailResponseDto(scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id =" + id)));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String author, String password) {

        if (title == null || author == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Title or author or password cannot be null");
        }

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id =" + id));

        if (!password.equals(schedule.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        schedule.update(title, author);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id, String password) {
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password cannot be null");
        }

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id =" + id));

        if (!password.equals(schedule.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
