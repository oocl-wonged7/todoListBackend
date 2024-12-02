package com.bootcamp.todoList.controller;

import com.bootcamp.todoList.model.TodoItem;
import com.bootcamp.todoList.service.TodoItemService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public TodoItem addTodoItem(@RequestBody TodoItem todoItem) {
        System.out.println("POST /todoItems");
        return todoItemService.addTodoItem(todoItem);
    }

    @PutMapping("/{id}")
    public TodoItem updateTodoItem(@PathVariable Integer id, @RequestBody TodoItem todoItem) {
        System.out.println("PUT /todoItems/" + id);
        return todoItemService.updateTodoItem(id, todoItem);
    }
}
