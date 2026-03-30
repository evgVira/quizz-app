package com.example.quizzapp.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsWithOptionsResponseDto {

    List<QuestionResponseDto> questions;
    List<OptionResponseDto> options;

}
