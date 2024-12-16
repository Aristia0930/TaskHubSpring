package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name="todosdata")
@NoArgsConstructor
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean checked;
    private String author;
    private Timestamp created_at;
    private Timestamp updated_at;

    @Builder
    public Todo(String text,Boolean checked,String author,Timestamp created_at){
        this.text=text;
        this.checked=checked;
        this.author=author;
        this.created_at=created_at;

    }

}
