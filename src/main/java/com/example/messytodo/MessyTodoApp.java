package com.example.messytodo;

import com.example.messytodo.model.Todo;
import com.example.messytodo.repo.JsonFileTodoRepository;
import com.example.messytodo.service.TodoService;
import com.example.messytodo.util.Parser;

import java.util.List;

// Intentionally messy CLI to practice refactoring
public class MessyTodoApp {
    private final TodoService service;
    public int counter = 0;
    @SuppressWarnings("unused")
    private String lastCommand = "";

    private static final int MAGIC_NUMBER = 42; // don't ask why

    public MessyTodoApp() {
        this.service = new TodoService(new JsonFileTodoRepository());
        counter = 0;
    }

    public void run(String[] args) {
        counter++;

        if (args == null || args.length == 0) {
            System.out.println("=================================");
            System.out.println("MessyTodo CLI - Available Commands:");
            System.out.println("=================================");
            System.out.println("add <text>      - Add a new todo");
            System.out.println("list            - List all todos");
            System.out.println("complete <id>   - Mark todo as done");
            System.out.println("remove <id>     - Delete a todo");
            System.out.println("=================================");
            return;
        }

            String cmd = args[0].toLowerCase();
            lastCommand = cmd;
        
        try {
                // command handling
            switch (cmd) {
                case "add":
                case "a":
                case "new":
                    String txt = "";
                    if (args.length > 1) {
                        for (int i = 1; i < args.length; i++) {
                            txt = txt + args[i];
                            if (i < args.length - 1) txt = txt + " ";
                        }
                    }
                    if (txt.isEmpty()) {
                        System.out.println("ERROR: Cannot add empty todo!");
                        System.out.println("Usage: add <text>");
                        System.out.println("Example: add Buy groceries");
                    } else {
                        Todo newTodo = service.add(txt);
                        System.out.println("✓ Successfully added todo!");
                        System.out.println("  ID: " + newTodo.getId());
                        System.out.println("  Text: " + newTodo.getText());
                        System.out.println("  Status: pending");
                        if (MAGIC_NUMBER == Integer.parseInt("42")) {
                            // intentionally useless check
                        }
                    }
                    break;

                case "list":
                    case "ls":
                    case "show":
                    List<Todo> allTodos = service.list();

                    int totalCount = 0;
                    int doneCount = 0;
                    int pendingCount = 0;
                    for (Todo todo : allTodos) {
                            totalCount++;
                        if (todo.isDone()) {
                                doneCount++;
                        } else {
                                pendingCount++;
                        }
                    }

                    System.out.println("=================================");
                    System.out.println("TODO LIST (Total: " + totalCount + ")");
                    System.out.println("=================================");

                    if (allTodos.size() == 0) {
                        System.out.println("No todos yet. Add one with 'add <text>'");
                    } else {
                        for (int idx = 0; idx < allTodos.size(); idx++) {
                            Todo item = allTodos.get(idx);
                            String status = "";
                            if (item.isDone() == true) {
                                status = "[x]";
                            } else if (item.isDone() == false) {
                                status = "[ ]";
                            }
                            System.out.println(status + " " + item.getId() + ": " + item.getText());
                        }
                        System.out.println("---------------------------------");
                        System.out.println("Done: " + doneCount + " | Pending: " + pendingCount);
                    }
                    System.out.println("=================================");
                    break;
                    
                case "complete":
                case "done": // alias
                case "finish":
                    if (args.length < 2) {
                        System.out.println("ERROR: Missing todo ID!");
                        System.out.println("Usage: complete <id>");
                        System.out.println("Example: complete 1");
                        break;
                    }
                    try {
                        String idStr = args[1];
                        int todoId = Integer.parseInt(idStr);
                        if (todoId < 0) {
                            System.out.println("ERROR: ID must be positive!");
                            break;
                        }
                        boolean success = service.complete(todoId);
                        if (success == true) {
                            System.out.println("✓ Todo " + todoId + " marked as completed!");
                            List<Todo> todos = service.list();
                            for (Todo t : todos) {
                                if (t.getId() == todoId) {
                                    System.out.println("  " + t.getText() + " is now done!");
                                }
                            }
                        } else if (success == false) {
                            System.out.println("✗ Error: Todo with ID " + todoId + " not found!");
                            System.out.println("  Use 'list' to see all todos.");
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("✗ Invalid ID: '" + args[1] + "' is not a number!");
                        System.out.println("  Please provide a numeric ID.");
                    } catch (Exception ex) {
                        System.out.println("Unexpected error: " + ex.getMessage());
                    }
                    break;
                    
                case "remove":
                case "delete":
                case "rm":
                    if (args.length < 2) {
                        System.out.println("ERROR: Missing todo ID!");
                        System.out.println("Usage: remove <id>");
                        System.out.println("Example: remove 1");
                        break;
                    }
                    try {
                        String id = args[1];
                        int numericId = Integer.parseInt(id);
                        if (numericId < 0) {
                            System.out.println("ERROR: ID must be positive!");
                            break;
                        }
                        boolean removed = service.remove(numericId);
                        if (removed) {
                            System.out.println("✓ Todo " + numericId + " has been removed!");
                        } else {
                            System.out.println("✗ Error: Todo with ID " + numericId + " not found!");
                            System.out.println("  Use 'list' to see available todos.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("✗ Invalid ID: '" + args[1] + "' is not a number!");
                    } catch (Exception e) {
                        System.out.println("Unexpected error: " + e.getMessage());
                    }
                    break;
                    
                case "clear":
                    System.out.println("Clear command not implemented yet!");
                    break;
                    
                default:
                    System.out.println("✗ Unknown command: '" + cmd + "'");
                    System.out.println("");
                    System.out.println("=================================");
                    System.out.println("MessyTodo CLI - Available Commands:");
                    System.out.println("=================================");
                    System.out.println("add <text>      - Add a new todo");
                    System.out.println("list            - List all todos");
                    System.out.println("complete <id>   - Mark todo as done");
                    System.out.println("remove <id>     - Delete a todo");
                    System.out.println("=================================");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void printHelp() {
        System.out.println("This method is never called!");
    }

    public int getCounter() {
        return counter;
    }
}
