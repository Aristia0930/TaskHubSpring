package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class User  implements Serializable {
//    private static final long serialVersionUID = 1L; // UID 버전 추가

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
