package com.example.quizzapp.storage.repository;

import com.example.quizzapp.storage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByLogin(String userLogin);

}
