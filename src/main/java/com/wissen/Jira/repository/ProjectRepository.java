package com.wissen.Jira.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wissen.Jira.models.Project;


@Repository
public class ProjectRepository 
{
    private final List<Project> projects = new ArrayList<>();

    public List<Project> findAll()
    {
        return projects;
    }

    public void save(Project project)
    {
        projects.add(project);
    }
    
    public Project findById(Long id)
    {
        return projects.stream()
                                .filter(p -> p.getId().equals(id))
                                .findFirst()
                                .orElse(null);
    }

    public void delete(Long id)
    {
        projects.removeIf(p-> p.getId().equals(id));
    }
         
}
