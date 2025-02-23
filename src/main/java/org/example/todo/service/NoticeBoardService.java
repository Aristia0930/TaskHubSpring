package org.example.todo.service;

import org.example.todo.dto.NoticBoardDto;
import org.example.todo.entity.NoticeBoard;
import org.example.todo.exception.customexception.NoticeException;
import org.example.todo.exception.customexception.ProfileUpdateException;
import org.example.todo.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardService {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;
    //게시글 등록
    public void writeNotic(String id, NoticBoardDto noticBoardDto){
        NoticeBoard noticeBoard= NoticeBoard.builder()
                .title(noticBoardDto.getTitle())
                .content(noticBoardDto.getContent())
                .writer(id)
                .build();
        try {
            noticeBoardRepository.save(noticeBoard);
        }
        catch (Exception e){
            throw new NoticeException(id,e);
        }
    }

    //예외처리해주기
    public List<NoticeBoard> getNoticeBoards(){

        return noticeBoardRepository.findAllSortedByCreatedDate();
    }

}
