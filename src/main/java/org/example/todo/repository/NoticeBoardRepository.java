package org.example.todo.repository;

import org.example.todo.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Long> {

    @Query("SELECT n FROM NoticeBoard n ORDER BY n.created_date DESC ")
    List<NoticeBoard> findAllSortedByCreatedDate();
}
