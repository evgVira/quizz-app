package com.example.quizzapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultResponseDto {

    private Long questionCount;
    private Long correctAnswerCount;

}
