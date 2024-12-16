package org.example.todo.controller;

import org.example.todo.entity.User;
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

import javax.swing.plaf.PanelUI;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
        String authorities = authentication.getAuthorities().toString();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("authorities", authorities);

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
        userInfo.put("authorities", authentication.getAuthorities().toString());

        return ResponseEntity.ok(userInfo);

    }

    @GetMapping("/logoutok")
    public ResponseEntity<Void> logoutok() {


        return ResponseEntity.ok().build();
    }
}
