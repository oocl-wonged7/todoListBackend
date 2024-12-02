package com.bootcamp.todoList.service;

import com.bootcamp.todoList.model.TodoItem;
import com.bootcamp.todoList.repository.TodoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {
    private final TodoItemRepository todoItemRepository;

    public TodoItemService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public List<TodoItem> getAllTodoItems() {
        return todoItemRepository.findAll();
    }

    public TodoItem addTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    public TodoItem updateTodoItem(Integer id, TodoItem todoItem) {
        TodoItem existingTodoItem = todoItemRepository.findById(id).orElse(null);
        if (existingTodoItem == null) {
            return null;
        }
        existingTodoItem.setText(todoItem.getText());
        return todoItemRepository.save(existingTodoItem);
    }
}
