package org.example.todo.controller;

import org.example.todo.dto.NoticBoardDto;
import org.example.todo.service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    NoticeBoardService noticeBoardService;

    //관리자 글 작성
    @PostMapping("/write")
    public ResponseEntity<Void> writeNotic(@RequestBody NoticBoardDto notic){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        noticeBoardService.writeNotic(id,notic);



        return ResponseEntity.ok().build();
    }

    //공지 게시글 수정

    //공지 게시글 삭제


}
