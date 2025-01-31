package org.example.todo.exception.customexception;

public class MessageSendException extends  RuntimeException{
    public MessageSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
