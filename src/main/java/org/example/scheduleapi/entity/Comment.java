package org.example.scheduleapi.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedule_comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String contents;
    private String author;
    private String password;

    @Builder
    public Comment(Long id, Schedule schedule, String contents, String author,  String password) {
        this.id = id;
        this.schedule = schedule;
        this.contents = contents;
        this.author = author;
        this.password = password;
    }

}
