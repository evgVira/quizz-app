package com.example.quizzapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionResponseDto {

    private String optionId;
    private String optionText;
    private Integer orderIndex;
    private Boolean isCorrect;
    private String questionId;

}
