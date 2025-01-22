package org.example.todo.exception;

import jakarta.persistence.EntityNotFoundException;
import org.example.todo.exception.customexception.DuplicateUserException;
import org.example.todo.exception.customexception.ProfileNotFoundException;
import org.example.todo.exception.customexception.ProfileUpdateException;
import org.example.todo.exception.customexception.SendMailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException e){
        //409코드리턴
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> handleProfileNotFoundException(ProfileNotFoundException e) {
        //추후 로깅으로 남기기
        System.out.println(e.getId());
        //404코드 리턴
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ProfileUpdateException.class)
    public ResponseEntity<String> handleProfileUpdateException(ProfileUpdateException e) {
        //추후 로깅으로 남기기
        System.out.println(e.getId()+" "+e.getCause());
        //404코드 리턴
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SendMailException.class)
    public ResponseEntity<String> handleSendMailException(SendMailException e) {
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("이메일 전송 오류: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("확인하지 못한 에러발생.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
