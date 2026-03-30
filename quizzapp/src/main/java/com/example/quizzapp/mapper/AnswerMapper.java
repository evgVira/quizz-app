package com.example.quizzapp.mapper;

import com.example.quizzapp.storage.entity.Answer;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.UserResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AnswerMapper {

    public Answer mapToAnswer(UserResponse userResponse, Option option){
        Answer answer = new Answer();
        answer.setId(UUID.randomUUID().toString());
        answer.setOption(option);
        answer.setUserResponse(userResponse);
        return answer;
    }
}
