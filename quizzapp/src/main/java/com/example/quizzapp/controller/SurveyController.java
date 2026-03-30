package com.example.quizzapp.controller;

import com.example.quizzapp.dto.*;
import com.example.quizzapp.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/surveyController")
@CrossOrigin
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/createSurvey")
    public ResponseWrapperDto<SurveyResponseDto> createSurvey(@RequestBody SurveyCreateDto surveyCreateDto) {
        return surveyService.createSurvey(surveyCreateDto);
    }

    @PostMapping("/addQuestionsWithOptionsToSurvey")
    public ResponseWrapperDto<Void> addQuestionsWithOptionsToSurvey(@RequestBody QuestionsWithOptionsCreateDto questionsWithOptionsCreateDto) {
        return surveyService.addQuestionsWithOptionsToSurvey(questionsWithOptionsCreateDto);
    }

    @GetMapping("/getSurveyList")
    public ResponseWrapperDto<List<SurveyResponseDto>> getSurveyList() {
        return surveyService.getSurveyList();
    }

    @GetMapping("/getSurveyDetails/{surveyId}")
    public ResponseWrapperDto<SurveyDetailsResponseDto> getSurveyDetails(@PathVariable("surveyId") String surveyId) {
        return surveyService.getSurveyDetails(surveyId);
    }

}
