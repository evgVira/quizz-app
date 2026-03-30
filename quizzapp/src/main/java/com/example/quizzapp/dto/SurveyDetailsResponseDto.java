package com.example.quizzapp.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDetailsResponseDto {

    private SurveyResponseDto survey;
    private List<QuestionResponseDto> questions;
    private List<OptionResponseDto> options;

}
