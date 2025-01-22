package org.example.todo.exception.customexception;

import lombok.Getter;

@Getter
public class ProfileNotFoundException extends RuntimeException{
    private String id;

    public ProfileNotFoundException(String id) {
        super("현재 이아디에 대한 프로필 정보를 찾을수가 없습니다.");
        this.id=id;
    }
}
