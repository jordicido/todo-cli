package com.example.messytodo;

public class Main {
    // very small bootstrap that delegates to MessyTodoApp
    public static void main(String[] args) {
        MessyTodoApp app = new MessyTodoApp();
        app.run(args);
    }
}
