package com.example.quizzapp.storage.repository;

import com.example.quizzapp.storage.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

    @Query(value = "select * from quizz_sc.question where survey_id like :surveyId", nativeQuery = true)
    List<Question> findQuestionsBySurvey(@Param("surveyId") String surveyId);

    @Query(value = "select count(*) from quizz_sc.question where survey_id like :surveyId", nativeQuery = true)
    Long getQuestionCountBySurveyId(@Param("surveyId") String surveyId);

}
