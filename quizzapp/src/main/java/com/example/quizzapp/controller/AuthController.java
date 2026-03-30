package com.example.quizzapp.controller;

import com.example.quizzapp.dto.AuthRequestDto;
import com.example.quizzapp.dto.AuthResponseDto;
import com.example.quizzapp.dto.ResponseWrapperDto;
import com.example.quizzapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authController")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseWrapperDto<AuthResponseDto> auth(@RequestBody AuthRequestDto authRequestDto) {
        return authService.createAuthToken(authRequestDto);
    }

}
