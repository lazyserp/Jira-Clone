package com.wissen.Jira.service;

import org.springframework.stereotype.Service;

import com.wissen.Jira.repository.ProjectRepository;
import com.wissen.Jira.repository.TaskRepository;
import com.wissen.Jira.exceptions.ProjectNotFoundException;
import com.wissen.Jira.models.Project;
import com.wissen.Jira.models.Task;
import com.wissen.Jira.dtos.TaskRequest;
import com.wissen.Jira.dtos.TaskResponse;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class TaskService 
{
    private final TaskRepository taskRepo;
    private final ProjectRepository projectRepo;

    public TaskService(TaskRepository taskRepo , ProjectRepository projectRepo)
    {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
    }

    @Transactional
    public TaskResponse createTask(UUID projectId , TaskRequest request)
    {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not Found!"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());

        // Sync bidirectional relationship using parent helper
        project.addTask(task);

        Task saved = taskRepo.save(task);
        return mapToTaskResponse(saved);
    }
    
    private TaskResponse mapToTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus(), task.getProject().getId());
    }
}
