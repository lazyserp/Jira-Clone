package com.wissen.Jira.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.Jira.dtos.ProjectRequest;
import com.wissen.Jira.dtos.ProjectResponse;
import com.wissen.Jira.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController 
{
    private final ProjectService service;

    public ProjectController(ProjectService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest project) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProject(project));
    }
    
    @GetMapping("")
    public Page<ProjectResponse> getAllProjects(Pageable pageable)
    {
        //call the api via : http://localhost:8080/projects?page=1&size=10&sort=name
        return service.getAllProjects(pageable);
    }
    
    @GetMapping("/{id}")
    public ProjectResponse getProject(@PathVariable UUID id)
    {
        return service.getProject(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id)
    {
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }    
}

