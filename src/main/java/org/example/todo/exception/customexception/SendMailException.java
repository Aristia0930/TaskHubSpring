package org.example.todo.exception.customexception;

public class SendMailException extends RuntimeException{
    public SendMailException(String message,Throwable cause) {
        super(message,cause);
    }

}

