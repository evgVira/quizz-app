package com.example.quizzapp.service.impl;

import com.example.quizzapp.mapper.AnswerMapper;
import com.example.quizzapp.service.AnswerService;
import com.example.quizzapp.storage.entity.Answer;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.UserResponse;
import com.example.quizzapp.storage.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    private final static String ANSWER_CREATE_MESSAGE = "Ответ пользователя сохранен";

    @Override
    @Transactional
    public void addUserAnswer(UserResponse userResponse, Option option) {
        Answer answer = answerMapper.mapToAnswer(userResponse, option);
        answerRepository.save(answer);
        log.info(ANSWER_CREATE_MESSAGE);
    }

    @Override
    public List<Answer> getUserAnswers(String userResponseId) {
        return answerRepository.getAnswersByUserResponseId(userResponseId);
    }

    @Override
    @Transactional
    public void removeAnswersByUserResponseIds(List<String> userResponseIds) {
        List<Answer> answersForRemove = answerRepository.getAnswersByUserResponseIds(userResponseIds);
        answerRepository.deleteAll(answersForRemove);
        log.info("Ответы пользователя для теста были удалены");
    }
}
