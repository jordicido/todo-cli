package com.example.messytodo.repo;

import com.example.messytodo.model.Todo;

import java.util.List;

public interface TodoRepository {
    Todo save(String text);
    List<Todo> findAll();
    Todo findById(int id);
    boolean delete(int id);
    void update(Todo todo);
}
