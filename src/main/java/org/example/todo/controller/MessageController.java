package org.example.todo.controller;

import org.example.todo.entity.Message;
import org.example.todo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    

    @Autowired
    private MessageService messageService;

    //받은 메세지 확인페이지
    //현재 내아이디로 온 메세지만 검색
    @GetMapping("/box")
    public List<Message> messageBox(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        return messageService.messageBox(id);


    }
    //내가 보낸 메세지 확인
    @GetMapping("/mysend")
    public List<Message> myMessage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        return messageService.myMessage(id);
        
    }


    //메세지 보내기
    //보낼 사람과 받을사람

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        int rs=messageService.sendMessage(message,id);

        if (rs==1){
            return ResponseEntity.ok().build();
        } else if (rs==-1) {
            return ResponseEntity.status(999).build();

        } else{
            return ResponseEntity.badRequest().build();
        }


    }
    
    //메세지 삭제
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> onRemove(@PathVariable Long id){

        int rs= messageService.onRemove(id);
        if (rs==1){
            return ResponseEntity.ok().build();

        }else{
            return ResponseEntity.badRequest().build();
        }

    }

    //보낸거 자기 자신에게서 삭제
    @PutMapping("/remove/{id}")
    public ResponseEntity<Void> checkRemove(@PathVariable Long id){

        int rs= messageService.checkRemove(id);
        if (rs==1){
            return ResponseEntity.ok().build();

        }else{
            return ResponseEntity.badRequest().build();
        }

    }

}
