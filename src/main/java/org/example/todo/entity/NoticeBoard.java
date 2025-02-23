package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name="notice_board")
@Data
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String writer;

    private String title;

    private String content;

    private Timestamp created_date = new Timestamp(System.currentTimeMillis()); // 메시지 전송 시간 (생성 시 현재 시간)

    @Builder
    public NoticeBoard(String writer,String title,String content){
        this.writer=writer;
        this.title=title;
        this.content=content;
    }


}
