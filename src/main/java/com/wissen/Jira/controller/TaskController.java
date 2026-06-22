package com.wissen.Jira.controller;

import com.wissen.Jira.dtos.TaskRequest;
import com.wissen.Jira.dtos.TaskResponse;
import com.wissen.Jira.models.TaskStatus;
import com.wissen.Jira.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for Task operations.
 *
 * Responsibilities:
 *  - Parse and validate HTTP requests
 *  - Delegate all business logic to TaskService
 *  - Return appropriate HTTP status codes and response bodies
 *
 * This controller never talks to the repository or mappers directly.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /tasks/projects/{projectId}
    // Creates a new task under the given project
    @PostMapping("/projects/{projectId}")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable UUID projectId,
            @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /tasks/{taskId}
    // Returns a single task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable UUID taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    // GET /tasks?status=TODO
    // Returns all tasks with the given status
    @GetMapping
    public ResponseEntity<List<TaskResponse>> findByStatus(@RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.findByStatus(status));
    }

    // PUT /tasks/{taskId}
    // Updates an existing task's title and/or status
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID taskId,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    // DELETE /tasks/{taskId}
    // Deletes a task permanently
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
