package com.example.quizzapp.storage.repository;

import com.example.quizzapp.storage.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, String> {

    @Query(value = "update quizz_sc.user_response set score = :score, finish_try_time = :finishTryTime where id like :userResponseId", nativeQuery = true)
    @Modifying
    void saveScore(@Param("score") Long score, @Param("userResponseId") String userResponseId,
                   @Param("finishTryTime") Instant finishTryTime);

    @Query(value = "select * from quizz_sc.user_response where survey_id like :surveyId", nativeQuery = true)
    List<UserResponse> findUserResponseBySurveyId(@Param("surveyId") String surveyId);

}
