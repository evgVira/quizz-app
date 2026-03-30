package com.example.quizzapp.storage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "user_response", schema = "quizz_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    // попытка прохождения опроса

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "start_try_time")
    private Instant startTryTime;

    @Column(name = "finish_try_time")
    private Instant finishTryTime;

    @Column(name = "score")
    private Integer score;

}
