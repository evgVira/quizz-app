package com.example.quizzapp.storage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question", schema = "quizz_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    // вопрос

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    private Survey survey;

}
