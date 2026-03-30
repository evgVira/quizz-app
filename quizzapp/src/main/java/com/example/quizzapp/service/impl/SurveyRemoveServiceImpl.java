package com.example.quizzapp.service.impl;

import com.example.quizzapp.dto.ResponseWrapperDto;
import com.example.quizzapp.dto.SurveyRemoveResponseDto;
import com.example.quizzapp.service.SurveyRemoveService;
import com.example.quizzapp.service.SurveyService;
import com.example.quizzapp.service.UserResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SurveyRemoveServiceImpl implements SurveyRemoveService {

    private final QuestionServiceImpl questionService;
    private final UserResponseService userResponseService;
    private final SurveyService surveyService;

    @Override
    @Transactional
    public ResponseWrapperDto<SurveyRemoveResponseDto> removeSurvey(String surveyId) {
        ResponseWrapperDto<SurveyRemoveResponseDto> removeResponseDtoResponseWrapperDto = new ResponseWrapperDto<>();
        questionService.removeQuestionBySurveyId(surveyId);
        log.info("Выполнен метод removeQuestionBySurveyId");
        userResponseService.removeUserResponseBySurveyId(surveyId);
        log.info("Выполнен метод removeUserResponseBySurveyId");
        surveyService.removeSurveyById(surveyId);
        log.info("Выполнен метод removeSurveyById");
        SurveyRemoveResponseDto surveyRemoveResponseDto = new SurveyRemoveResponseDto();
        surveyRemoveResponseDto.setSurveyId(surveyId);
        surveyRemoveResponseDto.setStatus(true);
        return removeResponseDtoResponseWrapperDto.buildSuccessResponse(surveyRemoveResponseDto);
    }

}
