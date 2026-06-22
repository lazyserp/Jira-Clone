package com.wissen.Jira.exceptions;

/**
 * Thrown when a Task with the given ID cannot be found in the database.
 * Handled by GlobalExceptionHandler → returns HTTP 404 with a structured error body.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
