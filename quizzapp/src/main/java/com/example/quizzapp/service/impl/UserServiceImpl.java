package com.example.quizzapp.service.impl;

import com.example.quizzapp.service.UserEntityService;
import com.example.quizzapp.storage.entity.UserEntity;
import com.example.quizzapp.storage.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserEntityService, UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserEntity getUserById(String userId) {
        Optional<UserEntity> user = userEntityRepository.findById(userId);
        return user.orElse(null);
    }

    public UserEntity getUserByLogin(String userLogin) {
        Optional<UserEntity> user = userEntityRepository.findByLogin(userLogin);
        return user.orElse(null);
    }

    @Override
    public String getUserIdByLogin(String userLogin) {
        UserEntity user = getUserByLogin(userLogin);
        if(user != null){
            return user.getId();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Не найден пользователь!");
        }
        return new User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .toList()
        );
    }
}
