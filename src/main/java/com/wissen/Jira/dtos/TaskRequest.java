package com.wissen.Jira.dtos;

import com.wissen.Jira.models.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Inbound DTO for creating or updating a Task.
 * Validation lives here — NOT on the entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Task title cannot be empty!")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters!")
    private String title;

    /**
     * Status must be one of: TODO, IN_PROGRESS, DONE, CANCELLED.
     * Jackson will reject unknown enum values with a descriptive 400 error automatically.
     */
    @NotNull(message = "Status is required! Accepted values: TODO, IN_PROGRESS, DONE, CANCELLED")
    private TaskStatus status;
}
