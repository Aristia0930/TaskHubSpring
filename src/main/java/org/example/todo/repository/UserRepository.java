package org.example.todo.repository;

import org.example.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

    //id로 검색
    User findByUserId(String userId);


}
