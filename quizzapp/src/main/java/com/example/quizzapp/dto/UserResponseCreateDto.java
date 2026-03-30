package com.example.quizzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseCreateDto {

    private String surveyId;
    private String userId;
    private Instant startTryTime;
    private Instant finishTryTime;

}
