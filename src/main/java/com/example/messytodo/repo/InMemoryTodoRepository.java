package com.example.messytodo.repo;

import com.example.messytodo.model.Todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// intentionally simple in-memory repository
public class InMemoryTodoRepository implements TodoRepository {
    private final List<Todo> store = new ArrayList<>();
    private int nextId = 1;

    public Todo save(String text) {
        Todo t = new Todo(nextId++, text);
        store.add(t);
        return t;
    }

    public List<Todo> findAll() {
        // return a shallow copy
        return new ArrayList<>(store);
    }

    public Todo findById(int id) {
        for (Todo t : store) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public boolean delete(int id) {
        Iterator<Todo> it = store.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public void update(Todo todo) {
        // no-op for in-memory (todo is already updated by reference)
    }
}
