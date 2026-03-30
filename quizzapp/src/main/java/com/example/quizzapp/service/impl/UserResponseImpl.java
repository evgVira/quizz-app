package com.example.quizzapp.service.impl;

import com.example.quizzapp.dto.*;
import com.example.quizzapp.mapper.UserResponseMapper;
import com.example.quizzapp.service.*;
import com.example.quizzapp.storage.entity.*;
import com.example.quizzapp.storage.repository.UserResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserResponseImpl implements UserResponseService {

    private final UserResponseRepository userResponseRepository;
    private final SurveyService surveyService;
    private final UserEntityService userEntityService;
    private final UserResponseMapper userResponseMapper;
    private final AnswerService answerService;
    private final OptionService optionService;
    private final QuestionService questionService;

    private final static String SURVEY_NOT_FOUND = "Не удалось найти опрос";
    private final static String USER_NOT_FOUND_ERROR_MESSAGE = "Не удалось найти пользователя";
    private final static String USER_RESPONSE_CREATE_MESSAGE = "Данные о прохождении опроса были сохранены";
    private final static String OPTION_NOT_FOUND_MESSAGE = "Вариант ответа не был найден";
    private final static String USER_RESPONSE_NOT_FOUND_MESSAGE = "Не удалось найти данные о прохождении запроса";
    private final static String SAVE_SCORE_MESSAGE = "Значение подсчета правильных ответов было сохранено";


    @Override
    @Transactional
    public ResponseWrapperDto<UserResponseDto> addUserResponse(UserResponseCreateDto userResponseCreateDto) {
        ResponseWrapperDto<UserResponseDto> responseWrapperDto = new ResponseWrapperDto<>();
        Survey survey = surveyService.getSurveyById(userResponseCreateDto.getSurveyId());

        if (survey == null) {
            log.info(SURVEY_NOT_FOUND);
            return responseWrapperDto.buildErrorResponse(SURVEY_NOT_FOUND);
        }

        UserEntity userEntity = userEntityService.getUserById(userResponseCreateDto.getUserId());

        if (userEntity == null) {
            log.info(USER_NOT_FOUND_ERROR_MESSAGE);
            return responseWrapperDto.buildErrorResponse(USER_NOT_FOUND_ERROR_MESSAGE);
        }

        UserResponse userResponse = userResponseMapper.mapToUserResponse(userResponseCreateDto, survey, userEntity);
        userResponseRepository.save(userResponse);
        log.info(USER_RESPONSE_CREATE_MESSAGE);
        UserResponseDto userResponseDto = userResponseMapper.mapToUserResponseDto(userResponse);
        return responseWrapperDto.buildSuccessResponse(userResponseDto);
    }

    @Override
    @Transactional
    public ResponseWrapperDto<Void> addUserAnswer(UserAnswerCreateDto userAnswerCreateDto) {
        ResponseWrapperDto<Void> responseWrapperDto = new ResponseWrapperDto<>();
        Option option = optionService.getOptionById(userAnswerCreateDto.getOptionId());

        if (option == null) {
            log.info(OPTION_NOT_FOUND_MESSAGE);
            return responseWrapperDto;
        }

        UserResponse userResponse = findUserResponseById(userAnswerCreateDto.getUserResponseId());

        if (userResponse == null) {
            log.info(USER_RESPONSE_NOT_FOUND_MESSAGE);
            return responseWrapperDto.buildErrorResponse(USER_RESPONSE_NOT_FOUND_MESSAGE);
        }

        answerService.addUserAnswer(userResponse, option);
        return responseWrapperDto.buildSuccessResponse();
    }


    @Override
    @Transactional
    public ResponseWrapperDto<TestResultResponseDto> finishTest(String userResponseId) {
        ResponseWrapperDto<TestResultResponseDto> responseWrapperDto = new ResponseWrapperDto<>();
        UserResponse userResponse = findUserResponseById(userResponseId);

        if (userResponse == null) {
            log.info(USER_RESPONSE_NOT_FOUND_MESSAGE);
            return responseWrapperDto.buildErrorResponse(USER_RESPONSE_NOT_FOUND_MESSAGE);
        }

        List<Answer> answerList = answerService.getUserAnswers(userResponse.getId());

        if (!CollectionUtils.isEmpty(answerList)) {
            Long questionCount = questionService.getQuestionCountBySurveyId(userResponse.getSurvey().getId());
            Long answerCorrectCount = answerList.stream()
                    .map(Answer::getOption)
                    .map(Option::getIsCorrect)
                    .filter(Boolean.TRUE::equals)
                    .count();
            userResponseRepository.saveScore(answerCorrectCount, userResponseId, Instant.now());
            log.info(SAVE_SCORE_MESSAGE);
            TestResultResponseDto testResultResponseDto = new TestResultResponseDto();
            testResultResponseDto.setQuestionCount(questionCount);
            testResultResponseDto.setCorrectAnswerCount(answerCorrectCount);
            return responseWrapperDto.buildSuccessResponse(testResultResponseDto);
        }

        return null;
    }

    @Override
    public void removeUserResponseBySurveyId(String surveyId) {
        List<UserResponse> userResponsesForRemove = userResponseRepository.findUserResponseBySurveyId(surveyId);
        List<String> userResponseIds = userResponsesForRemove.stream()
                .map(UserResponse::getId)
                .toList();
        answerService.removeAnswersByUserResponseIds(userResponseIds);
        userResponseRepository.deleteAll(userResponsesForRemove);
        log.info("Попытки прохождения теста были удалены");
    }

    private UserResponse findUserResponseById(String userResponseId) {
        Optional<UserResponse> userResponse = userResponseRepository.findById(userResponseId);
        return userResponse.orElse(null);
    }

}
