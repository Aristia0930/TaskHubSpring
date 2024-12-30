package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNumber;
    private String name;
    private String userId;
    private String password;
    private String role;

    @Builder
    public User(String name,String userId,String password,String role){
        this.name=name;
        this.userId=userId;
        this.password=password;
        this.role=role;
    }



}
