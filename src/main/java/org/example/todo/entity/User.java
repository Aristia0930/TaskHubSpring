package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
@ToString
public class User  implements Serializable {
//    private static final long serialVersionUID = 1L; // UID 버전 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNumber;
    private String name;
//    @Column(name = "user_id")
    private String userId;
    private String password;
    private String role;

//    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
//    private Profile profile;




    @Builder
    public User(String name,String userId,String password,String role){

        this.name=name;
        this.userId=userId;
        this.password=password;
        this.role=role;

    }



}
