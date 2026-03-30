package com.example.quizzapp.mapper;

import com.example.quizzapp.dto.UserResponseCreateDto;
import com.example.quizzapp.dto.UserResponseDto;
import com.example.quizzapp.storage.entity.Survey;
import com.example.quizzapp.storage.entity.UserEntity;
import com.example.quizzapp.storage.entity.UserResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserResponseMapper {

    public UserResponse mapToUserResponse(UserResponseCreateDto userResponseCreateDto,
                                          Survey survey, UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(UUID.randomUUID().toString());
        userResponse.setSurvey(survey);
        userResponse.setUserEntity(userEntity);
        userResponse.setStartTryTime(userResponseCreateDto.getStartTryTime());
        userResponse.setFinishTryTime(userResponseCreateDto.getFinishTryTime());
        return userResponse;
    }

    public UserResponseDto mapToUserResponseDto(UserResponse userResponse) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userResponse.getId());
        userResponseDto.setSurveyId(userResponse.getSurvey().getId());
        userResponseDto.setCreatedById(userResponse.getUserEntity().getId());
        userResponseDto.setStartTryTime(userResponse.getStartTryTime());
        userResponseDto.setFinishTryTime(userResponse.getFinishTryTime());
        userResponseDto.setScore(userResponse.getScore());
        return userResponseDto;
    }
}
