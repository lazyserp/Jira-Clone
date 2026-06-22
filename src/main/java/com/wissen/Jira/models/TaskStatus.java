package com.wissen.Jira.models;

/**
 * Represents the lifecycle state of a Task.
 * Using an enum ensures only valid statuses can be persisted
 * and eliminates free-text typo bugs.
 */
public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE,
    CANCELLED
}
