package com.example.quizzapp.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsWithOptionsCreateDto {

    private String surveyId;
    private List<QuestionBodyCreateDto> questionsWithOptions;

}
