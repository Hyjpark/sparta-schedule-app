package org.example.scheduleapi.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.CommentRequestDto;
import org.example.scheduleapi.dto.CommentResponseDto;
import org.example.scheduleapi.entity.Schedule;
import org.example.scheduleapi.repository.CommentRepository;
import org.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponseDto saveComment(Long id, CommentRequestDto requestDto) {
        if (requestDto.getContents() == null || requestDto.getPassword() == null || requestDto.getAuthor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and author name are required");
        }

        if (requestDto.getContents().length() > 100) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only post 100 comments.");

        Schedule schedule = scheduleRepository.getOne(id);

        int commentCount = commentRepository.countByScheduleId(id);

        if (commentCount == 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only post up to 10 comments.");
        }

        return new CommentResponseDto(commentRepository.save(requestDto.toEntity(schedule)));
    }
}
