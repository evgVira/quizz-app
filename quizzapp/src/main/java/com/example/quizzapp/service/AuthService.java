package com.example.quizzapp.service;

import com.example.quizzapp.dto.AuthRequestDto;
import com.example.quizzapp.dto.AuthResponseDto;
import com.example.quizzapp.dto.ResponseWrapperDto;

public interface AuthService {

    ResponseWrapperDto<AuthResponseDto> createAuthToken(AuthRequestDto authRequestDto);

}
