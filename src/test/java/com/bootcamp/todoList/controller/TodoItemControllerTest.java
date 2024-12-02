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
        todoItemRepository.deleteAll();
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

    @Test
    void should_add_todo_item() throws Exception {
        TodoItem todoItem = new TodoItem("Buy butter");
        String todoItemRequestString = todoItemJson.write(todoItem).getJson();
        String todoItemResponseString = client.perform(MockMvcRequestBuilders.post("/todoItems")
                .contentType("application/json")
                .content(todoItemRequestString))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TodoItem addedTodoItem = todoItemJson.parseObject(todoItemResponseString);
        assertThat(addedTodoItem).usingRecursiveComparison().ignoringFields("id").isEqualTo(todoItem);
    }

    @Test
    void should_update_todo_item() throws Exception {
        TodoItem firstTodoItem = todoItemRepository.findAll().get(0);
        firstTodoItem.setText("Buy margarine");
        String todoItemRequestString = todoItemJson.write(firstTodoItem).getJson();
        String todoItemResponseString = client.perform(MockMvcRequestBuilders.put("/todoItems/" + firstTodoItem.getId())
                .contentType("application/json")
                .content(todoItemRequestString))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TodoItem updatedTodoItem = todoItemJson.parseObject(todoItemResponseString);
        assertThat(updatedTodoItem).usingRecursiveComparison().isEqualTo(firstTodoItem);
    }

    @Test
    void should_toggle_todo_item() throws Exception {
        TodoItem firstTodoItem = todoItemRepository.findAll().get(0);
        firstTodoItem.setDone(!firstTodoItem.getDone());
        String todoItemRequestString = todoItemJson.write(firstTodoItem).getJson();
        String todoItemResponseString = client.perform(MockMvcRequestBuilders.put("/todoItems/" + firstTodoItem.getId())
                .contentType("application/json")
                .content(todoItemRequestString))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TodoItem updatedTodoItem = todoItemJson.parseObject(todoItemResponseString);
        assertThat(updatedTodoItem).usingRecursiveComparison().isEqualTo(firstTodoItem);
    }

    @Test
    void should_delete_todo_item() throws Exception {
        TodoItem firstTodoItem = todoItemRepository.findAll().get(0);
        client.perform(MockMvcRequestBuilders.delete("/todoItems/" + firstTodoItem.getId()))
                .andExpect(status().isOk());

        List<TodoItem> expectedTodoItems = todoItemRepository.findAll();
        String todoItemsResponseString = client.perform(MockMvcRequestBuilders.get("/todoItems"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<TodoItem> todoItems = todoItemsJson.parseObject(todoItemsResponseString);
        assertThat(todoItems).usingRecursiveComparison().isEqualTo(expectedTodoItems);
    }

}
