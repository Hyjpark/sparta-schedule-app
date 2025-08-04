package org.example.scheduleapi.service;

import org.example.scheduleapi.dto.CommentRequestDto;
import org.example.scheduleapi.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto saveComment(Long id, CommentRequestDto requestDto);
}
