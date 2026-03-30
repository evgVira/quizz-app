package com.example.quizzapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {

    private String questionId;
    private String questionText;
    private Integer orderIndex;

}
