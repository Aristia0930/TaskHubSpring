package org.example.todo.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.todo.entity.Todo;
import org.example.todo.exception.customexception.TodoDataException;
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
        List<Todo> todos=todoRepository.findByAuthor(id);
        return todoRepository.findByAuthor(id);
    }

    //입력하기
    public void onInsert(Todo todo,String id){
        try {
            Todo newTodo = Todo.builder()
                    .text(todo.getText())
                    .checked(todo.getChecked())
                    .author(id)
                    .created_at(Timestamp.from(Instant.now()))
                    .build();

            todoRepository.save(newTodo); // 데이터베이스에 저장


        } catch (DataAccessException e) {
            e.printStackTrace();  // 예외 처리
            // 데이터베이스 관련 예외 처리
            throw new TodoDataException("insert오류",e);
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();  // 예외 처리
            throw new TodoDataException("알수 업는 오류",e);
        }
    }
    public void onRemove(Long num){
        try {
            todoRepository.deleteById(num);
        }catch (Exception e){
            throw new TodoDataException("todo삭제 오류",e);
        }

    }

    public void onToggle(Long id, Boolean checked) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 Todo 항목을 찾을 수 없습니다: " + id));

        todo.setChecked(checked);
        todo.setUpdated_at(Timestamp.from(Instant.now()));

        todoRepository.save(todo); // JPA의 변경 감지를 활용
    }

    public void onModify(Long id, String text) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 Todo 항목을 찾을 수 없습니다: " + id));

        todo.setText(text);
        todo.setUpdated_at(Timestamp.from(Instant.now()));

        todoRepository.save(todo); // JPA의 변경 감지를 활용
    }
}
