package com.bootcamp.todoList.controller;

import com.bootcamp.todoList.model.TodoItem;
import com.bootcamp.todoList.repository.TodoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TodoItemControllerTest {
    @Autowired
    private MockMvc client;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private JacksonTester<List<TodoItem>> todoItemsJson;

    @Autowired
    private JacksonTester<TodoItem> todoItemJson;

    @BeforeEach
    void setUp() {
        todoItemRepository.findAll().clear();
        todoItemRepository.save(new TodoItem("Buy milk"));
        todoItemRepository.save(new TodoItem("Buy eggs"));
        todoItemRepository.save(new TodoItem("Buy bread"));
    }

    @Test
    void should_return_all_todo_items() throws Exception {
        List<TodoItem> expectedTodoItems = todoItemRepository.findAll();
        String todoItemsResponseString = client.perform(MockMvcRequestBuilders.get("/todoItems"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<TodoItem> todoItems = todoItemsJson.parseObject(todoItemsResponseString);
        assertThat(todoItems).usingRecursiveComparison().isEqualTo(expectedTodoItems);
    }

}
