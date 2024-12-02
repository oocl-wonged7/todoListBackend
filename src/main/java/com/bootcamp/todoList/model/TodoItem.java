package com.bootcamp.todoList.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private Boolean done;


    public TodoItem(String text) {
        this.text = text;
        this.done = false;
    }

    public TodoItem() {
        this.text = "";
        this.done = false;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getDone() {
        return done;
    }
}
