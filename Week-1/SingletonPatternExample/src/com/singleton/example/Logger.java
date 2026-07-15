package com.singleton.example;

public class Logger {
    private static Logger instance;
    private final StringBuilder logHistory = new StringBuilder();

    private Logger() {
        // private constructor prevents external instantiation
        System.out.println("Logger initialized.");
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        logHistory.append(message).append("\n");
        System.out.println("Log: " + message);
    }

    public String getLogHistory() {
        return logHistory.toString();
    }
}
