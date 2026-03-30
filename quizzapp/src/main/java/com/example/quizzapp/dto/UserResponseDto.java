package com.example.quizzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String id;
    private String surveyId;
    private String createdById;
    private Instant startTryTime;
    private Instant finishTryTime;
    private Integer score;
}
