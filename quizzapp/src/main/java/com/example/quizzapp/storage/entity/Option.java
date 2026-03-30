package com.example.quizzapp.storage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option", schema = "quizz_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {

    // вариант ответа

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

}
