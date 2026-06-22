package com.wissen.Jira.service;

import com.wissen.Jira.dtos.TaskRequest;
import com.wissen.Jira.dtos.TaskResponse;
import com.wissen.Jira.exceptions.ProjectNotFoundException;
import com.wissen.Jira.exceptions.TaskNotFoundException;
import com.wissen.Jira.mapper.TaskMapper;
import com.wissen.Jira.models.Project;
import com.wissen.Jira.models.Task;
import com.wissen.Jira.models.TaskStatus;
import com.wissen.Jira.repository.ProjectRepository;
import com.wissen.Jira.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Business logic for Task operations.
 *
 * Workflow for every operation:
 *   1. Validate inputs / fetch entities from DB
 *   2. Apply business rules
 *   3. Persist via repository
 *   4. Return a DTO (never the raw entity)
 */
@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final ProjectRepository projectRepo;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepo, ProjectRepository projectRepo, TaskMapper taskMapper) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.taskMapper = taskMapper;
    }

    // ── Create ───────────────────────────────────────────────────────────────

    @Transactional
    public TaskResponse createTask(UUID projectId, TaskRequest request) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));

        Task task = taskMapper.toEntity(request);

        // Use the helper method to keep both sides of the relationship in sync
        project.addTask(task);

        Task saved = taskRepo.save(task);
        return taskMapper.toResponse(saved);
    }

    // ── Read ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public TaskResponse getTask(UUID taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        return taskMapper.toResponse(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findByStatus(TaskStatus status) {
        return taskRepo.findByStatus(status)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    // ── Update ───────────────────────────────────────────────────────────────

    @Transactional
    public TaskResponse updateTask(UUID taskId, TaskRequest request) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());

        // No explicit save() needed — the entity is managed within the transaction
        return taskMapper.toResponse(task);
    }

    // ── Delete ───────────────────────────────────────────────────────────────

    @Transactional
    public void deleteTask(UUID taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        // Remove via parent to keep the bidirectional relationship in sync
        task.getProject().removeTask(task);
    }
}
