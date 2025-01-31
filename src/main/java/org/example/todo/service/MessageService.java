package org.example.todo.service;

import org.example.todo.entity.Message;
import org.example.todo.exception.customexception.DatabaseException;
import org.example.todo.exception.customexception.MessageRemoveException;
import org.example.todo.exception.customexception.MessageSendException;
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
        return messageRepository.findBySenderIdAndIsChecked(id,false);
    }

    public void sendMessage(Message message,String id){
        try {
            Message newMessage = Message.builder()
                    .sender_id(id)
                    .receiver_id(message.getReceiverId())
                    .content(message.getContent())
                    .build();
            messageRepository.save(newMessage);

        }
        catch (DataAccessException e) {
            throw new DatabaseException("유저아이디가 찾지 못함",e) ;

        }
        catch (Exception e){
           throw new MessageSendException("메세지 전송 실패",e);

        }
    }

    public void onRemove(Long id) {
        try {
            messageRepository.deleteById(id);

        }catch (Exception e){
            e.printStackTrace();  // 예외 처리
            throw new MessageRemoveException("삭제실패",e);

        }
    }

    public void checkRemove(Long id){
        Message message=messageRepository.findByMessageId(id);
        message.setIsChecked(true);
        try {
            messageRepository.save(message);

        }
        catch (Exception e){
            e.printStackTrace();  // 예외 처리
            throw new MessageRemoveException("삭제실패",e);
        }
    }
}
