package com.example.quizzapp.service.impl;

import com.example.quizzapp.dto.AuthRequestDto;
import com.example.quizzapp.dto.AuthResponseDto;
import com.example.quizzapp.dto.ResponseWrapperDto;
import com.example.quizzapp.service.AuthService;
import com.example.quizzapp.service.UserEntityService;
import com.example.quizzapp.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserEntityService userEntityService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseWrapperDto<AuthResponseDto> createAuthToken(AuthRequestDto authRequestDto) {
        ResponseWrapperDto<AuthResponseDto> responseWrapperDto = new ResponseWrapperDto<>();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(), authRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            return responseWrapperDto.buildErrorResponse("Некорректный логин или пароль");
        }

        UserDetails userDetails = userEntityService.loadUserByUsername(authRequestDto.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        String userId = userEntityService.getUserIdByLogin(authRequestDto.getLogin());
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAuthToken(token);
        authResponseDto.setCurrentUserId(userId);
        return responseWrapperDto.buildSuccessResponse(authResponseDto);
    }

}
