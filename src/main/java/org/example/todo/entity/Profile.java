package org.example.todo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long profileId;
//    private Long userNumber; // 보낸 사람 ID
    private String email;

    @Column(name = "img_id")
    private String imgId; // 받는 사람 ID

    private int point;

//    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId",insertable = false, updatable = false)
    private User user;

    @Builder
    public Profile(Long profile_id,String email,String img_id,int point){
        this.profileId=profile_id;
//        this.userNumber=userNumber;
        this.email=email;
        this.imgId=img_id;
        this.point=point;
    }

}
