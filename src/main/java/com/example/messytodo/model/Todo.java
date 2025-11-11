package com.example.messytodo.model;

// Minimal POJO
public class Todo {
    private final int id;
    private final String text;
    private boolean done;

    public Todo(int id, String text) {
        this.id = id;
        this.text = text;
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
