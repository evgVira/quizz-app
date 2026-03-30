package com.example.quizzapp.controller;

import com.example.quizzapp.dto.*;
import com.example.quizzapp.service.UserResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userResponseController")
@CrossOrigin
public class UserResponseController {

    private final UserResponseService userResponseService;

    @PostMapping("/addUserResponse")
    public ResponseWrapperDto<UserResponseDto> addUserResponse(@RequestBody UserResponseCreateDto userResponseCreateDto) {
        return userResponseService.addUserResponse(userResponseCreateDto);
    }

    @PostMapping("/addUserAnswer")
    public ResponseWrapperDto<Void> addUserAnswer(@RequestBody UserAnswerCreateDto userAnswerCreateDto) {
        return userResponseService.addUserAnswer(userAnswerCreateDto);
    }

    @PostMapping("/finishTest/{userResponseId}")
    public ResponseWrapperDto<TestResultResponseDto> finishTest(@PathVariable("userResponseId") String userResponseId) {
        return userResponseService.finishTest(userResponseId);
    }

}
