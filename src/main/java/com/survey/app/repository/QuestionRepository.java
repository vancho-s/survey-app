package com.survey.app.repository;

import com.survey.app.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select distinct q.id from Question q where q.id in :ids")
    Set<Long> findExistingQuestionsIdsByIds(@Param("ids") final Set<Long> questionIds);

    @Query("select q from Question q where q.id in :ids")
    Set<Question> findExistingQuestionsByIds(@Param("ids") final Set<Long> questionIds);
    
}
