package com.example.quizzapp.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBodyCreateDto {

    private String questionText;
    private int orderIndex;
    private List<OptionBodyCreateDto> options;

}
