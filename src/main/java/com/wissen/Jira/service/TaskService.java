package com.wissen.Jira.service;

import org.springframework.stereotype.Service;

import com.wissen.Jira.repository.ProjectRepository;
import com.wissen.Jira.repository.TaskRepository;
import com.wissen.Jira.exceptions.ProjectNotFoundException;
import com.wissen.Jira.models.Project;
import com.wissen.Jira.models.Task;

@Service
public class TaskService 
{
    private TaskRepository taskRepo;
    private ProjectRepository projectRepo ;

    public TaskService(TaskRepository taskRepo , ProjectRepository projectRepo)
    {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
    }

    public Task createTask(Long projectId , Task task)
    {
        Project project = projectRepo.findById(projectId).orElseThrow( () -> new ProjectNotFoundException("Project not Found!"));

        task.setProject(project);
        return taskRepo.save(task);
    }
    



    
}
