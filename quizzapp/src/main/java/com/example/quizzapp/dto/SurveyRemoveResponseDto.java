package com.example.quizzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRemoveResponseDto {

    private String surveyId;
    private Boolean status;
}
