package org.example.todo.service;

import org.example.todo.dto.NoticBoardDto;
import org.example.todo.entity.NoticeBoard;
import org.example.todo.exception.customexception.NoticeException;
import org.example.todo.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<NoticeBoard> rs;
        try{
            rs=    noticeBoardRepository.findAllSortedByCreatedDate();
        }catch (Exception e){
            throw new NoticeException("전체불러오기 오류",e);
        }
        return rs;
    }

    public Optional<NoticeBoard> getNoticeBoard(Long id){

        Optional<NoticeBoard> rs;
        try{
            rs=noticeBoardRepository.findById(id);
        }catch (Exception e){
            throw new NoticeException("공지 상세 조회 오류",e);
        }
        return rs;
    }



}
