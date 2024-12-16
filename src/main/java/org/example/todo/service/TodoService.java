package org.example.todo.service;

import org.example.todo.entity.Todo;
import org.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;


    //todo 넘버 확인
    public Long todoNumber(){
        return todoRepository.count();
    }

    //todo 목록 불러오기
    public List<Todo> getTodoData(String id){
        return todoRepository.findByAuthor(id);
    }

    //입력하기
    public int onInsert(Todo todo,String id){
        try {
            Todo newTodo = Todo.builder()
                    .text(todo.getText())
                    .checked(todo.getChecked())
                    .author(id)
                    .created_at(Timestamp.from(Instant.now()))
                    .build();

            todoRepository.save(newTodo); // 데이터베이스에 저장

            return 1; // 성공 시 1 반환
        } catch (DataAccessException e) {
            // 데이터베이스 관련 예외 처리
            System.err.println("Database error occurred while saving Todo: " + e.getMessage());
            return 0; // 실패 시 0 반환
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("Unexpected error occurred while saving Todo: " + e.getMessage());
            return -1; // 기타 실패 시 -1 반환
        }
    }
    public int onRemove(Long num){
        try {
            todoRepository.deleteById(num);
            return 1;
        }catch (Exception e){
            e.printStackTrace();  // 예외 처리
            return 0;
        }

    }

    public int onToggle(Long id,Boolean checked){
        Optional<Todo> todoOptional=todoRepository.findById(id);
        if (!todoOptional.isPresent()) {
            return 0;
        }
        Todo todo = todoOptional.get();
        Todo newTodo = Todo.builder()
                .text(todo.getText())
                .checked(checked)
                .author(todo.getAuthor())
                .created_at(todo.getCreated_at())
                .build();
        newTodo.setId(id);
        newTodo.setUpdated_at(Timestamp.from(Instant.now()));

        try {
            todoRepository.save(newTodo);
            return 1;
        }catch (Exception e){
            e.printStackTrace();  // 예외 처리
            return -1;
        }

    }

    public int onModify(Long id, String text) {

        Optional<Todo> todoOptional=todoRepository.findById(id);
        if (!todoOptional.isPresent()) {
            return 0;
        }
        Todo todo = todoOptional.get();
        Todo newTodo = Todo.builder()
                .text(text)
                .checked(todo.getChecked())
                .author(todo.getAuthor())
                .created_at(todo.getCreated_at())
                .build();
        newTodo.setId(id);
        newTodo.setUpdated_at(Timestamp.from(Instant.now()));

        try {
            todoRepository.save(newTodo);
            return 1;
        }catch (Exception e){
            e.printStackTrace();  // 예외 처리
            return -1;
        }
    }
}
