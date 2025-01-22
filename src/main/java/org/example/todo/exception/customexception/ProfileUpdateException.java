package org.example.todo.exception.customexception;

import lombok.Getter;

@Getter
public class ProfileUpdateException extends RuntimeException{
    private String id;

    public ProfileUpdateException(String id,Throwable cause) {
        super("현재 이아디에 대한 프로필 업데이트 실패.",cause);
        this.id=id;
    }
}
