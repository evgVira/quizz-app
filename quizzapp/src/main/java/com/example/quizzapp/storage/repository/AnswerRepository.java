package com.example.quizzapp.storage.repository;

import com.example.quizzapp.storage.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {

    @Query(value = "select * from quizz_sc.answer where user_response_id like :userResponseId", nativeQuery = true)
    List<Answer> getAnswersByUserResponseId(@Param("userResponseId") String userResponseId);

    @Query(value = "select * from quizz_sc.answer where user_response_id in :userResponseIds", nativeQuery = true)
    List<Answer> getAnswersByUserResponseIds(@Param("userResponseIds") List<String> userResponseIds);
}
