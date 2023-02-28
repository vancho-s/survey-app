package com.survey.app.repository;

import com.survey.app.domain.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {

    @Query("select distinct ur from UserResponse ur join fetch ur.user u join fetch ur.question q join fetch q.subject s where u.id = :userId and q.enabled = true")
    List<UserResponse> findUserResponsesByUserId(@Param("userId") final long userId);

    @Query("select distinct ur from UserResponse ur join fetch ur.user u join fetch ur.question q join fetch q.subject s where u.id = :userId and s.id in :subjectIds and q.enabled = true")
    List<UserResponse> findResponsesByUserIdAndSubjectsIds(@Param("userId") final long userId, @Param("subjectIds") final Set<Long> subjectIds);

    @Query("select distinct ur from UserResponse ur join fetch ur.user u join fetch ur.question q join fetch q.subject s where u.id = :userId and q.id in :questionIds and q.enabled = true")
    List<UserResponse> findUserResponsesByUserIdAndQuestionIds(@Param("userId") final long userId, @Param("questionIds") final Set<Long> questionIds);

    @Query(value = "SELECT distinct ur.question_id FROM USER_RESPONSES ur WHERE ur.user_id = :id and ur.question_id in :ids", nativeQuery = true)
    Set<Long> findUserAnsweredResponses(@Param("id") final long userId, @Param("ids") final Set<Long> questionIds);

}
