package com.wissen.Jira.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wissen.Jira.models.Project;
import com.wissen.Jira.repository.ProjectRepository;
import com.wissen.Jira.exceptions.ProjectNotFoundException;
import com.wissen.Jira.dtos.ProjectRequest;
import com.wissen.Jira.dtos.ProjectResponse;
import com.wissen.Jira.dtos.TaskResponse;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService 
{
    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo)
    {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponse> getAllProjects(Pageable pageable)
    {
        Page<Project> res =  repo.findAll(pageable);
        Page<ProjectResponse> pagedResponse = res.map(project -> mapToProjectResponse(project));
        return pagedResponse;
    }

    @Transactional
    public ProjectResponse createProject(ProjectRequest request)
    {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        Project saved = repo.save(project);
        return mapToProjectResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProject(UUID id)
    {
        Project project = repo.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not Found!"));
        return mapToProjectResponse(project);
    }

    @Transactional
    public void deleteProject(UUID id)
    {
        if (!repo.existsById(id)) {
            throw new ProjectNotFoundException("Project not Found!");
        }
        repo.deleteById(id);
    }

    private ProjectResponse mapToProjectResponse(Project project) {
        List<TaskResponse> taskResponses = project.getTasks().stream()
                .map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getStatus(), project.getId()))
                .toList();
        return new ProjectResponse(project.getId(), project.getName(), project.getDescription(), taskResponses);
    }
}
