package com.bootcamp.todoList.exception;


public class TodoItemNotFoundException extends RuntimeException {
    public TodoItemNotFoundException() {
        super("Todo item not found");
    }
}
