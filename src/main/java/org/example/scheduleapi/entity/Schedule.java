package org.example.scheduleapi.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String contents;
    private String author;
    private String password;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Schedule(String title, String contents, String author, String password) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
    }

    public void update(String title, String author) {
        this.title = title;
        this.author = author;
    }

}
