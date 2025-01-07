package org.example.todo.repository;

import org.example.todo.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    @Query("SELECT p FROM Profile p JOIN FETCH p.user u WHERE u.userId = :userId")
    Profile findProfileWithUserByUserId(@Param("userId") String userId);


}
