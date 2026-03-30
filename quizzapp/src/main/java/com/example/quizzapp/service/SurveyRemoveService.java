package com.example.quizzapp.service;

import com.example.quizzapp.dto.ResponseWrapperDto;
import com.example.quizzapp.dto.SurveyRemoveResponseDto;

public interface SurveyRemoveService {

    ResponseWrapperDto<SurveyRemoveResponseDto> removeSurvey(String surveyId);
}
