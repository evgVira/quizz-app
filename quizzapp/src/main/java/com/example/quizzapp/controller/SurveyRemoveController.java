package com.example.quizzapp.controller;

import com.example.quizzapp.dto.ResponseWrapperDto;
import com.example.quizzapp.dto.SurveyRemoveResponseDto;
import com.example.quizzapp.service.SurveyRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveyRemoveController")
@RequiredArgsConstructor
@CrossOrigin
public class SurveyRemoveController {

    private final SurveyRemoveService surveyRemoveService;

    @PostMapping("/removeSurvey/{surveyId}")
    public ResponseWrapperDto<SurveyRemoveResponseDto> removeSurvey(@PathVariable("surveyId") String surveyId) {
        return surveyRemoveService.removeSurvey(surveyId);
    }

}
