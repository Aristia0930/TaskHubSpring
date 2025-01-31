package org.example.todo.exception.customexception;

public class MessageRemoveException extends  RuntimeException{

    public MessageRemoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
