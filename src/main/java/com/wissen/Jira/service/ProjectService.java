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

    public ProjectService(ProjectRepository repo)
    {
        this.repo = repo;
    }

    public List<Project> getAllProjects()
    {
        return repo.findAll();
    }

    public Project createProject(Project project)
    {
        return repo.save(project);
    }

    public Project getProject(long id)
    {
        return repo.findById(id).orElseThrow(() ->  new ProjectNotFoundException("Project not Found!"));

    }

    public void deleteProject(long id)
    {
        repo.deleteById(id);
    }

}
