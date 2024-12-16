package org.example.todo.repository;

import org.example.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    //현재 그 아이디로 작성한todo 목록 물러오기
    List<Todo> findByAuthor(String author);





}
