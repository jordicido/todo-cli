package com.example.messytodo.repo;

import com.example.messytodo.model.Todo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Repository that persists todos in a JSON file (intentionally messy)
public class JsonFileTodoRepository implements TodoRepository {
    private final List<Todo> store = new ArrayList<>();
    private int nextId = 1;
    private final String filePath;
    private final Gson gson;

    public JsonFileTodoRepository(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        loadFromFile();
    }

    public JsonFileTodoRepository() {
        this("todos.json");
    }

    // messy: doesn't handle errors well
    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return; // nothing to load
        }

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<TodoData>>(){}.getType();
            List<TodoData> dataList = gson.fromJson(reader, listType);
            
            if (dataList != null) {
                for (TodoData data : dataList) {
                    Todo todo = new Todo(data.id, data.text);
                    if (data.done) {
                        todo.setDone(true);
                    }
                    store.add(todo);
                    if (data.id >= nextId) {
                        nextId = data.id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading todos: " + e.getMessage());
        }
    }

    // messy: doesn't handle errors well
    private void saveToFile() {
        List<TodoData> dataList = new ArrayList<>();
        for (Todo t : store) {
            dataList.add(new TodoData(t.getId(), t.getText(), t.isDone()));
        }

        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            System.err.println("Error saving todos: " + e.getMessage());
        }
    }

    public Todo save(String text) {
        Todo t = new Todo(nextId++, text);
        store.add(t);
        saveToFile(); // persist immediately
        return t;
    }

    public List<Todo> findAll() {
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
                saveToFile(); // persist immediately
                return true;
            }
        }
        return false;
    }

    public void update(Todo todo) {
        saveToFile(); // persist immediately
    }

    // Simple data class for JSON serialization
    private static class TodoData {
        int id;
        String text;
        boolean done;

        TodoData(int id, String text, boolean done) {
            this.id = id;
            this.text = text;
            this.done = done;
        }
    }
}
