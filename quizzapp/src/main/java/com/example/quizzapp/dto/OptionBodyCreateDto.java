package com.example.quizzapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionBodyCreateDto {

    private String optionText;
    private Integer orderIndex;
    private Boolean isCorrect;

}
