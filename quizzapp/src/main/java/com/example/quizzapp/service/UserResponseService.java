package com.example.quizzapp.service;

import com.example.quizzapp.dto.*;

public interface UserResponseService {

    ResponseWrapperDto<UserResponseDto> addUserResponse(UserResponseCreateDto userResponseCreateDto);

    ResponseWrapperDto<Void> addUserAnswer(UserAnswerCreateDto userAnswerCreateDto);

    ResponseWrapperDto<TestResultResponseDto> finishTest(String userResponseId);

    void removeUserResponseBySurveyId(String surveyId);

}
