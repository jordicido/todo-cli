package com.example.messytodo.service;

import com.example.messytodo.model.Todo;
import com.example.messytodo.repo.TodoRepository;

import java.util.List;

public class TodoService {
    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public Todo add(String text) {
        return repo.save(text);
    }

    public List<Todo> list() {
        return repo.findAll();
    }

    public boolean complete(int id) {
        Todo t = repo.findById(id);
        if (t == null) return false;
        t.setDone(true);
        repo.update(t); // persist the change
        return true;
    }

    public boolean remove(int id) {
        return repo.delete(id);
    }
}
