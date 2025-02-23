package org.example.todo.repository;

import org.example.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    //id로 검색
    User findByUserId(String userId);


//    @Query("SELECT u FROM User u JOIN FETCH u.profile WHERE u.userNumber = :userNumber")
//    User findByUserIdWithProfile(@Param("userNumber") Long userNumber);



}
