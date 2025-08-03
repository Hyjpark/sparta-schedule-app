package org.example.scheduleapi.repository;

import org.example.scheduleapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByScheduleId(Long id);
}
