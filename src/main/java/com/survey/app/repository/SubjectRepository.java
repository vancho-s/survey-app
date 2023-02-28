package com.survey.app.repository;

import com.survey.app.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("select distinct s.id from Subject s where s.id in :ids")
    Set<Long> findSubjectIdsByIds(@Param("ids") final Set<Long> subjectIds);

    @Query("select s from Subject s left join fetch s.questions where s.id in :ids")
    Set<Subject> findSubjectsByIds(@Param("ids") final Set<Long> subjectIds);

}
