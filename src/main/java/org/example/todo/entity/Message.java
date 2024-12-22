package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name="messages")
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;
    @Column(name = "sender_id", nullable = false)
    private String senderId; // 보낸 사람 ID

    @Column(name = "receiver_id", nullable = false)
    private String receiverId; // 받는 사람 ID
    private String content;
    private Boolean is_checked = false; // 확인 여부 (기본값: false)
    private Timestamp sent_at = new Timestamp(System.currentTimeMillis()); // 메시지 전송 시간 (생성 시 현재 시간)

    @Builder
    public Message(String sender_id,String receiver_id,String content){
        this.senderId=sender_id;
        this.receiverId=receiver_id;
        this.content=content;
    }
}
