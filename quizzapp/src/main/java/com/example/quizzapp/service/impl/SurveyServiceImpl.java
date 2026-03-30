package com.example.quizzapp.service.impl;

import com.example.quizzapp.dto.*;
import com.example.quizzapp.mapper.SurveyMapper;
import com.example.quizzapp.service.QuestionService;
import com.example.quizzapp.service.SurveyService;
import com.example.quizzapp.service.UserEntityService;
import com.example.quizzapp.storage.entity.Survey;
import com.example.quizzapp.storage.entity.UserEntity;
import com.example.quizzapp.storage.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final UserEntityService userEntityService;
    private final QuestionService questionService;

    private final static String SURVEY_CREATE_MESSAGE = "Создание нового опроса surveyId = %s";
    private final static String USER_NOT_FOUND_ERROR_MESSAGE = "Не удалось найти пользователя";
    private final static String SURVEY_NOT_FOUND = "Не удалось найти опрос";

    @Override
    @Transactional
    public ResponseWrapperDto<SurveyResponseDto> createSurvey(SurveyCreateDto surveyCreateDto) {
        ResponseWrapperDto<SurveyResponseDto> responseWrapperDto = new ResponseWrapperDto<>();
        UserEntity userEntity = userEntityService.getUserById(surveyCreateDto.getCreatedById());

        if (userEntity == null) {
            log.info(USER_NOT_FOUND_ERROR_MESSAGE);
            return responseWrapperDto.buildErrorResponse(USER_NOT_FOUND_ERROR_MESSAGE);
        }

        Survey createdSurvey = surveyMapper.mapToSurvey(surveyCreateDto, userEntity);
        Survey savedSurvey = surveyRepository.save(createdSurvey);
        log.info(String.format(SURVEY_CREATE_MESSAGE, createdSurvey.getId()));
        SurveyResponseDto surveyResponseDto = surveyMapper.mapToSurveyResponseDto(savedSurvey);
        return responseWrapperDto.buildSuccessResponse(surveyResponseDto);
    }

    @Override
    public ResponseWrapperDto<SurveyDetailsResponseDto> getSurveyDetails(String surveyId) {
        ResponseWrapperDto<SurveyDetailsResponseDto> responseWrapperDto = new ResponseWrapperDto<>();
        Survey findedSurvey = findSurveyById(surveyId);

        if (findedSurvey == null) {
            log.info(SURVEY_NOT_FOUND);
            return responseWrapperDto.buildErrorResponse(SURVEY_NOT_FOUND);
        }

        SurveyResponseDto surveyResponse = surveyMapper.mapToSurveyResponseDto(findedSurvey);
        QuestionsWithOptionsResponseDto questionsWithOptionsResponseDto = questionService.getQuestionsWithOptionsResponseDto(surveyId);
        SurveyDetailsResponseDto surveyDetailsResponseDto = new SurveyDetailsResponseDto();
        surveyDetailsResponseDto.setSurvey(surveyResponse);
        surveyDetailsResponseDto.setQuestions(questionsWithOptionsResponseDto.getQuestions());
        surveyDetailsResponseDto.setOptions(questionsWithOptionsResponseDto.getOptions());
        return responseWrapperDto.buildSuccessResponse(surveyDetailsResponseDto);
    }

    @Override
    public ResponseWrapperDto<List<SurveyResponseDto>> getSurveyList() {
        ResponseWrapperDto<List<SurveyResponseDto>> responseWrapperDto = new ResponseWrapperDto<>();
        List<Survey> surveys = surveyRepository.findAll();

        if (!CollectionUtils.isEmpty(surveys)) {
            List<SurveyResponseDto> surveyResponseDtos = surveys.stream()
                    .map(surveyMapper::mapToSurveyResponseDto)
                    .toList();
            return responseWrapperDto.buildSuccessResponse(surveyResponseDtos);
        }

        return responseWrapperDto.buildSuccessResponse(List.of());
    }

    @Override
    @Transactional
    public ResponseWrapperDto<Void> addQuestionsWithOptionsToSurvey(QuestionsWithOptionsCreateDto questionsWithOptionsCreateDto) {
        ResponseWrapperDto<Void> responseWrapperDto = new ResponseWrapperDto<>();
        Survey survey = findSurveyById(questionsWithOptionsCreateDto.getSurveyId());

        if (survey == null) {
            log.info(SURVEY_NOT_FOUND);
            return responseWrapperDto.buildErrorResponse(SURVEY_NOT_FOUND);
        }

        questionService.createQuestions(questionsWithOptionsCreateDto.getQuestionsWithOptions(), survey);
        return responseWrapperDto.buildSuccessResponse();
    }

    @Override
    public Survey getSurveyById(String surveyId) {
        return findSurveyById(surveyId);
    }

    @Override
    @Transactional
    public void removeSurveyById(String surveyId) {
        Survey surveyForRemove = findSurveyById(surveyId);
        if (surveyForRemove != null) {
            surveyRepository.delete(surveyForRemove);
            log.info("Тест был удален");
        }
    }

    private Survey findSurveyById(String surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        return survey.orElse(null);
    }

}
