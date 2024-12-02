package com.bootcamp.todoList.controller;

import com.bootcamp.todoList.model.TodoItem;
import com.bootcamp.todoList.service.TodoItemService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todoItems")
@CrossOrigin(origins = "http://localhost:3000")
public class todoItemsController {
    private final TodoItemService todoItemService;

    public todoItemsController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping
    public List<TodoItem> getTodoList() {
        System.out.println("GET /todoItems");
        return todoItemService.getAllTodoItems();
    }
}
