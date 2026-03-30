package com.example.quizzapp.service;

import com.example.quizzapp.storage.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserEntityService {

    UserEntity getUserById(String userId);

    UserDetails loadUserByUsername(String userLogin);

    String getUserIdByLogin(String userLogin);

}
