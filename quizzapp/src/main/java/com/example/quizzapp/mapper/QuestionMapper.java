package com.example.quizzapp.mapper;

import com.example.quizzapp.dto.QuestionBodyCreateDto;
import com.example.quizzapp.dto.QuestionResponseDto;
import com.example.quizzapp.dto.QuestionWithOptions;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.Question;
import com.example.quizzapp.storage.entity.Survey;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Component
public class QuestionMapper {

    public List<QuestionWithOptions> mapToQuestions(List<QuestionBodyCreateDto> questions, Survey survey) {
        if (!CollectionUtils.isEmpty(questions)) {
            return questions.stream()
                    .map(currentQuestionData -> {

                        Question newQuestion = new Question();
                        newQuestion.setId(UUID.randomUUID().toString());
                        newQuestion.setQuestionText(currentQuestionData.getQuestionText());
                        newQuestion.setOrderIndex(currentQuestionData.getOrderIndex());
                        newQuestion.setSurvey(survey);

                        List<Option> options = OptionMapper.mapToOptions(currentQuestionData.getOptions(), newQuestion);

                        return new QuestionWithOptions(newQuestion, options);

                    })
                    .toList();
        }
        return List.of();
    }

    public List<QuestionResponseDto> mapQuestionsToResponseDto(List<Question> questions) {
        if (!CollectionUtils.isEmpty(questions)) {
            return questions.stream()
                    .map(question -> {
                        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
                        questionResponseDto.setQuestionId(question.getId());
                        questionResponseDto.setQuestionText(question.getQuestionText());
                        questionResponseDto.setOrderIndex(question.getOrderIndex());
                        return questionResponseDto;
                    }).toList();
        }
        return List.of();
    }

}
