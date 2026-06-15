package com.wissen.Jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.Jira.models.Project;
import com.wissen.Jira.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class projectController 
{
    private final ProjectService service;

    public projectController(ProjectService service)
    {
        this.service = service;
    }


    @PostMapping
    public void createProject(@RequestBody Project project) {
        service.createProject(project);
    }
    

    @GetMapping("")
    public List<Project> getAllProjects()
    {
        return service.getAllProjects();
    }
    

    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id)
    {
        return service.getProject(id);

    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id)
    {
        service.deleteProject(id);
    }    

}

