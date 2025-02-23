package org.example.todo.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.todo.dto.ProfileDto;
import org.example.todo.entity.Profile;
import org.example.todo.entity.User;
import org.example.todo.exception.customexception.DuplicateUserException;
import org.example.todo.exception.customexception.ProfileNotFoundException;
import org.example.todo.exception.customexception.ProfileUpdateException;
import org.example.todo.repository.ProfileRepository;
import org.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    private BCryptPasswordEncoder pwencoder=new BCryptPasswordEncoder();


    //회원가입
    public void join(User user){
        if(userRepository.findByUserId(user.getUserId())!=null){
            throw new DuplicateUserException("회원가입 중복으로 인한 오류");
        }
        String newPassword=pwencoder.encode(user.getPassword());
       User newUser=User.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .password(newPassword)
                .role("ROLE_USER")
                .build();

       userRepository.save(newUser);

    }


    //프로필데이터 받아오기
    public Profile myProfile(String userId){
        Profile profile = profileRepository.findProfileWithUserByUserId(userId);
        if (profile == null) {
            throw new ProfileNotFoundException(userId);
        }

        return profile;

    }

    public void updateProfile(ProfileDto profileDto,String id){
        Profile profile=profileRepository.findProfileWithUserByUserId(id);
        if (profile == null) {
            throw new ProfileNotFoundException(id);
        }
        User user=profile.getUser();

        profile.setEmail(profileDto.getEmail());
        profile.setImgId(profileDto.getImgId());
        user.setName(profileDto.getName());
        profile.setUser(user);
        try {
            profileRepository.save(profile);
        }
        catch (Exception e){
            throw new ProfileUpdateException(id,e);
        }
    }


    @Transactional
    public void deleteUser(String id) {
        Profile profile = profileRepository.findProfileWithUserByUserId(id);
        if (profile == null) {
            throw new EntityNotFoundException("프로필정보를 찾을수 없습니다" + id);
        }

        User user = profile.getUser();
        if (user == null) {
            throw new EntityNotFoundException("유저정보를 찾을수 없습니다"+id);
        }

        profileRepository.delete(profile);
        userRepository.delete(user);
    }

    public User getUserById(String id){
        return userRepository.findByUserId(id);
    }

}
