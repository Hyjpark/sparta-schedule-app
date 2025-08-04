package org.example.scheduleapi.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.CommentRequestDto;
import org.example.scheduleapi.dto.CommentResponseDto;
import org.example.scheduleapi.entity.Schedule;
import org.example.scheduleapi.error.CommentErrorCode;
import org.example.scheduleapi.error.GlobalErrorCode;
import org.example.scheduleapi.error.exception.RestApiException;
import org.example.scheduleapi.repository.CommentRepository;
import org.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponseDto saveComment(Long id, CommentRequestDto requestDto) {
        validate(requestDto);

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()
                -> new RestApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        if (commentRepository.countByScheduleId(id) == 10) {
            throw new RestApiException(CommentErrorCode.COMMENT_COUNT_EXCEEDED);
        }

        return new CommentResponseDto(commentRepository.save(requestDto.toEntity(schedule)));
    }

    private void validate(CommentRequestDto requestDto) {
        if (requestDto.getContents() == null || requestDto.getPassword() == null || requestDto.getAuthor() == null) {
            throw new RestApiException(GlobalErrorCode.INVALID_PARAMETER);
        }

        if (requestDto.getContents().length() > 100) throw new RestApiException(CommentErrorCode.CONTENTS_LENGTH_LIMITS);
    }
}
