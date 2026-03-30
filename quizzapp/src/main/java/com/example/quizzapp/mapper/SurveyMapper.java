package com.example.quizzapp.mapper;

import com.example.quizzapp.dto.SurveyCreateDto;
import com.example.quizzapp.dto.SurveyResponseDto;
import com.example.quizzapp.storage.entity.Survey;
import com.example.quizzapp.storage.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class SurveyMapper {

    public Survey mapToSurvey(SurveyCreateDto surveyCreateDto, UserEntity userEntity) {
        Survey survey = new Survey();
        survey.setId(UUID.randomUUID().toString());
        survey.setTitle(surveyCreateDto.getTitle());
        survey.setDescription(surveyCreateDto.getDescription());
        survey.setCreateDt(Instant.now());
        survey.setUpdateDt(Instant.now());
        survey.setIsActive(surveyCreateDto.isActive());
        survey.setCreatedBy(userEntity);
        return survey;
    }

    public SurveyResponseDto mapToSurveyResponseDto(Survey survey) {
        SurveyResponseDto responseDto = new SurveyResponseDto();
        responseDto.setSurveyId(survey.getId());
        responseDto.setTitle(survey.getTitle());
        responseDto.setDescription(survey.getDescription());
        responseDto.setCreateDt(survey.getCreateDt());
        responseDto.setUpdateDt(survey.getUpdateDt());
        responseDto.setIsActive(survey.getIsActive());
        responseDto.setUserId(survey.getCreatedBy().getId());
        responseDto.setUserLogin(survey.getCreatedBy().getLogin());
        return responseDto;
    }

}
