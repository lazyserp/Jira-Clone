package com.wissen.Jira.mapper;

import com.wissen.Jira.dtos.TaskRequest;
import com.wissen.Jira.dtos.TaskResponse;
import com.wissen.Jira.models.Task;
import org.springframework.stereotype.Component;

/**
 * TaskMapper centralises all conversions between Task entities and DTOs.
 *
 * Workflow:
 *   TaskRequest  ──toEntity()──▶  Task (entity)  ──▶  saved to DB
 *   Task (entity) ──toResponse()──▶  TaskResponse  ──▶  returned to client
 *
 * Keeping mapping logic here (not in the service or controller) means:
 *  - Services stay focused on business rules only
 *  - Mapping is easy to find, test, and change in one place
 */
@Component
public class TaskMapper {

    /**
     * Converts a TaskRequest DTO into a new Task entity.
     * The project association is NOT set here — that is done by the service
     * using Project#addTask() to keep the bidirectional relationship in sync.
     */
    public Task toEntity(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        return task;
    }

    /**
     * Converts a saved Task entity into a TaskResponse DTO.
     * Exposes only the fields that clients need — never the full entity.
     */
    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getProject() != null ? task.getProject().getId() : null
        );
    }
}
