package org.example.todo.controller;

import org.example.todo.entity.CustomUserDetails;
import org.example.todo.entity.Profile;
import org.example.todo.entity.User;
import org.example.todo.repository.ProfileRepository;
import org.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProfileRepository profileRepository;


    @GetMapping("/api/1")
    public String test(@RequestParam String text){
        System.out.println(text);
        return "성공";
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(User user){
        System.out.println("회원가입 진입");

        int rs= userService.join(user);

        if (rs==0){
            Map<String, Object> response = new HashMap<>();
            response.put("custom_code", 12);
            response.put("message", "회원가입 실패");
            System.out.println("회원가입실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        System.out.println("성공");
        return ResponseEntity.ok().build();

    }

    @GetMapping("/loginOk")
    public ResponseEntity<Map<String, String>> loginOk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        System.out.println("Authentication Object: " + authentication);
        System.out.println("User ID: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());



        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", id);
        CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
        String role=customUserDetails.getRole();
        userInfo.put("authorities", role);

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/check/1")
    public ResponseEntity<Map<String, String>> check(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        if(authentication==null || !authentication.isAuthenticated()|| Objects.equals(authentication.getName(), "anonymousUser")){

            return ResponseEntity.badRequest().build();
        }
        Map<String, String> userInfo = new HashMap<>();
        //String id = authentication.getName();

        CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
        String role=customUserDetails.getRole();

        userInfo.put("authorities", role);

        return ResponseEntity.ok(userInfo);

    }

    @GetMapping("/logoutok")
    public ResponseEntity<Void> logoutok() {


        return ResponseEntity.ok().build();
    }


    //유저 프로필 부분
    @GetMapping("/profile/mydata")
    public Map<String,String> myProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        System.out.println("프로필 "+id);

        Profile userProfile = profileRepository.findProfileWithUserByUserId(id); // userInfo를 기준으로 Profile 찾기

        System.out.println(userProfile);
        Map<String,String> profile=new HashMap<>();
        profile.put("email",userProfile.getEmail());
        profile.put("imgId",userProfile.getImgId());
        profile.put("point",Integer.toString(userProfile.getPoint()));
        profile.put("name",userProfile.getUser().getName());



        return profile;
    }
}
