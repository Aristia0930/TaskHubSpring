package org.example.todo.controller;

import jakarta.servlet.http.HttpSession;
import org.example.todo.dto.ProfileDto;
import org.example.todo.entity.CustomUserDetails;
import org.example.todo.entity.Profile;
import org.example.todo.entity.User;
import org.example.todo.repository.ProfileRepository;
import org.example.todo.service.MailService;
import org.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;




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
        String id = authentication.getName();
        Profile userProfile = userService.myProfile(id);
        CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
        String role=customUserDetails.getRole();

        userInfo.put("authorities", role);
        userInfo.put("imgId",userProfile.getImgId());

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

        Profile userProfile = userService.myProfile(id); // userInfo를 기준으로 Profile 찾기

        System.out.println(userProfile);
        Map<String,String> profile=new HashMap<>();
        profile.put("email",userProfile.getEmail());
        profile.put("imgId",userProfile.getImgId());
        profile.put("point",Integer.toString(userProfile.getPoint()));
        profile.put("name",userProfile.getUser().getName());




        return profile;
    }


    //이메일 테스트
    //리액트에서 이메일 주소 받으면
    //여기서는 인증번호를 던져준다. POST로 받자.아니다.. 세션으로 저장해서 받아오는게 안저하지 않을까?
    //세션 저장방식으로 하자
    //세션은 받아온 이메일로 만들어서 확인하자.
    @GetMapping("/profile/maile/check")
    public ResponseEntity<?> sendMail(@RequestParam String email, HttpSession session) {
        try {
            mailService.sendMail(email);
            session.setAttribute(email,mailService.getVerificationCode());
            return ResponseEntity.ok().body("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

    @GetMapping("/profile/maile/code")
    public ResponseEntity<?> code(@RequestParam String email, HttpSession session) {
        String code=(String) session.getAttribute(email);
        if(code!=null){
            return ResponseEntity.ok().body(code);
        }
        else{
            return ResponseEntity.badRequest().body("인증번호가 없습니다");
        }

    }
    //수정한값들을 다시 db로 보내서 업데이트해야한다.
    @PutMapping("/profile/update")
    public ResponseEntity<String> updateProfile(@RequestBody  ProfileDto profileDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        try{
            userService.updateProfile(profileDto,id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    





}
