package org.example.todo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NoticBoardDto {

    private String title;

    private String content;

    private Timestamp created_date;
}
