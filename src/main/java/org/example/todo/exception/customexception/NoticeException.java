package org.example.todo.exception.customexception;

public class NoticeException extends RuntimeException{
    public NoticeException(String message, Throwable cause) {
        super(message,cause);
    }

}


