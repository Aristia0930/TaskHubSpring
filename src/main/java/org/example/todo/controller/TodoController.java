package org.example.todo.controller;

import org.example.todo.entity.Todo;
import org.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    //현재 목록 확인
    @GetMapping("/number")
    public String todoNumber(){
        Long count=todoService.todoNumber();
        return count.toString();


    }

    //현재 아이디로 todo목록 불러오기
    @GetMapping("/data")
    public List<Todo> getTodoData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        List<Todo> data=todoService.getTodoData(id);
        return data;

    }

    //todo목록추가하기
    @PostMapping("/insert")
    public ResponseEntity<Void> onInsert(@RequestBody Todo todo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        todoService.onInsert(todo,id);

        return ResponseEntity.ok().build();


    }

    //todo목록 삭제
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> onRemove(@PathVariable Long id){

        todoService.onRemove(id);

        return ResponseEntity.ok().build();

    }

    //체크 반전
    @PutMapping("/toggle/{id}")
    public ResponseEntity<Void> onToggle(@PathVariable Long id,@RequestBody Map<String,Boolean> requestBody){
        Boolean checkValue=requestBody.get("checked");

        todoService.onToggle(id,checkValue);

            return ResponseEntity.ok().build();


    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> onModify(@PathVariable Long id,@RequestBody Map<String,String> requestBody){
        String text=requestBody.get("text");

        todoService.onModify(id,text);

        return ResponseEntity.ok().build();




    }



}
