package org.example.todo.exception.customexception;

public class TodoDataException extends RuntimeException {
    public TodoDataException(String message, Throwable cause) {
        super(message,cause);
    }
}
