package com.survey.app.repository;

import com.survey.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u.id from User u where u.id in :ids")
    List<Long> findExistingUsersByIds(@Param("ids") final List<Long> usersIds);

}
