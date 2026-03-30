package com.example.quizzapp.service;

import com.example.quizzapp.dto.*;
import com.example.quizzapp.storage.entity.Survey;

import java.util.List;

public interface SurveyService {

    ResponseWrapperDto<SurveyResponseDto> createSurvey(SurveyCreateDto surveyCreateDto);

    ResponseWrapperDto<Void> addQuestionsWithOptionsToSurvey(QuestionsWithOptionsCreateDto questionsWithOptionsCreateDto);

    ResponseWrapperDto<SurveyDetailsResponseDto> getSurveyDetails(String surveyId);

    ResponseWrapperDto<List<SurveyResponseDto>> getSurveyList();

    Survey getSurveyById(String surveyId);

    void removeSurveyById(String surveyId);

}
