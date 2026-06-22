package com.wissen.Jira.dtos;

import com.wissen.Jira.models.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Outbound DTO for a Task.
 * This is the only shape a Task ever takes in an API response —
 * the entity itself is never serialized directly.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private UUID id;
    private String title;
    private TaskStatus status;
    private UUID projectId;
}
