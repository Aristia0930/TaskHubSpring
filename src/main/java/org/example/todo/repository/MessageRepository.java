package org.example.todo.repository;

import org.example.todo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {


    //받는사람
    List<Message> findByReceiverId(String id);

    List<Message> findBySenderId(String id);
}
