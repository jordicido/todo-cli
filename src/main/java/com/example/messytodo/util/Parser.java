package com.example.messytodo.util;

public class Parser {
    // join args from index with spaces; messy: doesn't trim
    public static String joinArgs(String[] args, int start) {
        if (args == null || args.length <= start) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i > start) sb.append(' ');
            sb.append(args[i]);
        }
        return sb.toString();
    }
}
