package com.example.quizzapp.storage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer", schema = "quizz_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    // ответ на конкретный вопрос

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_response_id")
    private UserResponse userResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

}
