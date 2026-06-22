package com.wissen.Jira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.Jira.dtos.TaskRequest;
import com.wissen.Jira.dtos.TaskResponse;
import com.wissen.Jira.interfaces.TaskSummary;
import com.wissen.Jira.service.TaskService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController 
{
    private final TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    @PostMapping("/projects/{projectId}")
    public ResponseEntity<TaskResponse> createTask(@PathVariable UUID projectId, @Valid @RequestBody TaskRequest task)
    {
        TaskResponse response = taskService.createTask(projectId, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    // Using a Project (TaskSummary) similar to DTO
    public List<TaskSummary> findByStatus(@RequestParam String status)
    {   
        return taskService.findByStatus(status);
    }
}
