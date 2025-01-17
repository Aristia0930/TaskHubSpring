package org.example.todo.service;

import org.example.todo.dto.ProfileDto;
import org.example.todo.entity.Profile;
import org.example.todo.entity.User;
import org.example.todo.repository.ProfileRepository;
import org.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
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


    //프로필데이터 받아오기
    public Profile myProfile(String userId){

        return profileRepository.findProfileWithUserByUserId(userId);

    }

    public void updateProfile(ProfileDto profileDto,String id){
        Profile profile=profileRepository.findProfileWithUserByUserId(id);
        User user=profile.getUser();

        profile.setEmail(profileDto.getEmail());
        profile.setImgId(profileDto.getImgId());
        user.setName(profileDto.getName());
        profile.setUser(user);
        profileRepository.save(profile);
    }

}
