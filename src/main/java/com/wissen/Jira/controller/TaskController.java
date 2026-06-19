package com.wissen.Jira.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.Jira.service.TaskService;

import jakarta.validation.Valid;

import com.wissen.Jira.models.*;

@RestController
@RequestMapping("/tasks")
public class TaskController 
{
    private final TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }


    @PostMapping("/project/{projectId}")
    public Task createTask(@PathVariable Long projectId ,@Valid @RequestBody Task task)
    {
        return taskService.createTask(projectId, task);

    }

    
}
