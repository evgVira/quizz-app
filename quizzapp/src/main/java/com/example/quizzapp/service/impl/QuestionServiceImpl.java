package com.example.quizzapp.service.impl;

import com.example.quizzapp.dto.*;
import com.example.quizzapp.mapper.OptionMapper;
import com.example.quizzapp.mapper.QuestionMapper;
import com.example.quizzapp.service.OptionService;
import com.example.quizzapp.service.QuestionService;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.Question;
import com.example.quizzapp.storage.entity.Survey;
import com.example.quizzapp.storage.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final OptionService optionService;

    private final static String QUESTION_CREATE_MESSAGE = "Сохранение новых вопросов в базу данных";

    @Override
    @Transactional
    public void createQuestions(List<QuestionBodyCreateDto> questionBodyCreateDto, Survey survey) {
        List<QuestionWithOptions> questionWithOptions = questionMapper.mapToQuestions(questionBodyCreateDto, survey);

        if (!CollectionUtils.isEmpty(questionWithOptions)) {
            List<Question> createdQuestion = questionWithOptions.stream()
                    .map(QuestionWithOptions::getQuestion)
                    .toList();
            questionRepository.saveAll(createdQuestion);
            log.info(QUESTION_CREATE_MESSAGE);

            List<Option> createdOptions = questionWithOptions.stream()
                    .map(QuestionWithOptions::getOptions)
                    .flatMap(List::stream)
                    .toList();
            optionService.saveOptionsForQuestion(createdOptions);
        }
    }

    @Override
    public QuestionsWithOptionsResponseDto getQuestionsWithOptionsResponseDto(String surveyId) {
        List<Question> questions = questionRepository.findQuestionsBySurvey(surveyId);
        List<QuestionResponseDto> questionsResponse = questionMapper.mapQuestionsToResponseDto(questions);
        Set<String> questionIds = questions.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
        List<Option> options = optionService.getOptionsByQuestionIds(questionIds);
        List<OptionResponseDto> optionsResponse = OptionMapper.mapToOptionsResponseDto(options);

        QuestionsWithOptionsResponseDto response = new QuestionsWithOptionsResponseDto();
        response.setQuestions(questionsResponse);
        response.setOptions(optionsResponse);
        return response;
    }

    @Override
    @Transactional
    public void removeQuestionBySurveyId(String surveyId) {
        List<Question> questionsForRemove = questionRepository.findQuestionsBySurvey(surveyId);
        Set<String> questionIds = questionsForRemove.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
        optionService.removeOptionsByQuestionIds(questionIds);
        questionRepository.deleteAll(questionsForRemove);
        log.info("Вопросы для теста были удалены");
    }

    @Override
    public Long getQuestionCountBySurveyId(String surveyId) {
        return questionRepository.getQuestionCountBySurveyId(surveyId);
    }

}
