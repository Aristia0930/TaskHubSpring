package org.example.todo.service;

import org.example.todo.entity.User;
import org.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder pwencoder=new BCryptPasswordEncoder();


    //회원가입
    public int join(User user){
        if(userRepository.findByUserId(user.getUserId())!=null){
            return 0; // 회원가입 실패
        }
        String newPassword=pwencoder.encode(user.getPassword());
       User newUser=User.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .password(newPassword)
                .role("ROLE_USER")
                .build();

       userRepository.save(newUser);
       return 1; //회원가입 성공
    }


//    //프로필데이터 받아오기
//    public User myProfile(String userId){
//
//        return userRepository.findByUserIdWithProfile(userRepository.findByUserId(userId).getUserNumber());
////        return userRepository.findByUserId(userId);
//    }

}
