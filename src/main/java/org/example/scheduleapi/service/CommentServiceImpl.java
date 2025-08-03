package org.example.scheduleapi.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.CommentRequestDto;
import org.example.scheduleapi.dto.CommentResponseDto;
import org.example.scheduleapi.entity.Schedule;
import org.example.scheduleapi.repository.CommentRepository;
import org.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto saveComment(Long id, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepository.getOne(id);
        return new CommentResponseDto(commentRepository.save(requestDto.toEntity(schedule)));
    }
}
