package org.example.todo.controller;

import org.example.todo.dto.NoticBoardDto;
import org.example.todo.entity.NoticeBoard;
import org.example.todo.service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeBoardController {

    @Autowired
    NoticeBoardService noticeBoardService;
    //공지글 받아오기
    @GetMapping("/list")
    public ResponseEntity<List<NoticeBoard>> getNoticeBoards(){
        List<NoticeBoard> noticeBoard=noticeBoardService.getNoticeBoards();

        return ResponseEntity.ok(noticeBoard);
    }

    //댓글작성
    @PostMapping("/user/comment")
    public ResponseEntity<Void> commentCreate(){
        return ResponseEntity.ok().build();
    }

    //댓글 수정

    //댓글 삭제
}
