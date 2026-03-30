package com.example.quizzapp.service;

import com.example.quizzapp.storage.entity.Answer;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.UserResponse;

import java.util.List;

public interface AnswerService {

    void addUserAnswer(UserResponse userResponse, Option option);

    List<Answer> getUserAnswers(String userResponseId);

    void removeAnswersByUserResponseIds(List<String> userResponseIds);

}
