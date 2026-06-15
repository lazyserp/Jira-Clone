package com.wissen.Jira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wissen.Jira.models.Project;
import com.wissen.Jira.repository.ProjectRepository;
import com.wissen.Jira.exceptions.ProjectNotFoundException;

@Service
public class ProjectService 
{
    private final ProjectRepository repo;

    public ProjectService(ProjectRepository projectRepository)
    {
        repo = projectRepository;
    }

    public List<Project> getAllProjects()
    {
        return repo.findAll();
    }

    public void createProject(Project project)
    {
        repo.save(project);

    }

    public Project getProject(long id)
    {
        Project project  =  repo.findById(id);.
        
        if ( project == null)
            {
                throw new ProjectNotFoundException("Project not found with id : "+ id);
            }

        return project;
    }

    public void deleteProject(long id)
    {
        repo.delete(id);
    }
    
}
