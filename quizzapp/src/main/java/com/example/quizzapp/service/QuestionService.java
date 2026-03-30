package com.example.quizzapp.service;

import com.example.quizzapp.dto.QuestionBodyCreateDto;
import com.example.quizzapp.dto.QuestionsWithOptionsResponseDto;
import com.example.quizzapp.storage.entity.Survey;

import java.util.List;

public interface QuestionService {

    void createQuestions(List<QuestionBodyCreateDto> questionBodyCreateDto, Survey survey);

    QuestionsWithOptionsResponseDto getQuestionsWithOptionsResponseDto(String surveyId);

    Long getQuestionCountBySurveyId(String surveyId);

    void removeQuestionBySurveyId(String surveyId);

}
