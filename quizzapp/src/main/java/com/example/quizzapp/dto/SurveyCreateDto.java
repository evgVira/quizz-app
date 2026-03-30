package com.example.quizzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyCreateDto {

    private String title;
    private String description;
    private boolean isActive;
    private String createdById;

}
