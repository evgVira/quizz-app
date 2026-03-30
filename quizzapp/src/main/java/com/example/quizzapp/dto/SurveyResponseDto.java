package com.example.quizzapp.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponseDto {

    private String surveyId;
    private String title;
    private String description;
    private Instant createDt;
    private Instant updateDt;
    private Boolean isActive;
    private String userId;
    private String userLogin;

}
