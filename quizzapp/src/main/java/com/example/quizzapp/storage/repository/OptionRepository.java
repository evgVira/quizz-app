package com.example.quizzapp.storage.repository;

import com.example.quizzapp.storage.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OptionRepository extends JpaRepository<Option, String> {

    @Query(value = "select * from quizz_sc.option where question_id in :questionIds", nativeQuery = true)
    List<Option> findOptionByQuestionIds(@Param("questionIds") Set<String> questionIds);

}
