package com.example.quizzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerCreateDto {

    private String userResponseId;
    private String optionId;

}
