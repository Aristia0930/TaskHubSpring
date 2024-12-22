package org.example.todo.service;

import org.example.todo.entity.Message;
import org.example.todo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    //자기가 받은 메세지조회
    public List<Message> messageBox(String id){
        return messageRepository.findByReceiverId(id);
    }

    public List<Message> myMessage(String id){
        return messageRepository.findBySenderId(id);
    }

    public int sendMessage(Message message,String id){
        try {
            Message newMessage = Message.builder()
                    .sender_id(id)
                    .receiver_id(message.getReceiverId())
                    .content(message.getContent())
                    .build();
            messageRepository.save(newMessage);
            return 1;
        }
        catch (DataAccessException e) {
            return -1;
        }
        catch (Exception e){
            e.printStackTrace();  // 예외 처리
            return 0;
        }
    }

    public int onRemove(Long id) {
        try {
            messageRepository.deleteById(id);
            return 1;
        }catch (Exception e){
            e.printStackTrace();  // 예외 처리
            return 0;
        }
    }
}
