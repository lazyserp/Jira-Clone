package com.wissen.Jira.service;

import com.wissen.Jira.dtos.ProjectRequest;
import com.wissen.Jira.dtos.ProjectResponse;
import com.wissen.Jira.exceptions.ProjectNotFoundException;
import com.wissen.Jira.mapper.ProjectMapper;
import com.wissen.Jira.models.Project;
import com.wissen.Jira.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Business logic for Project operations.
 *
 * Workflow for every operation:
 *   1. Validate inputs / fetch entities from DB
 *   2. Apply business rules
 *   3. Persist via repository
 *   4. Return a DTO (never the raw entity)
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepo;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepo, ProjectMapper projectMapper) {
        this.projectRepo = projectRepo;
        this.projectMapper = projectMapper;
    }

    // ── Create ───────────────────────────────────────────────────────────────

    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.toEntity(request);
        Project saved = projectRepo.save(project);
        return projectMapper.toResponse(saved);
    }

    // ── Read ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<ProjectResponse> getAllProjects(Pageable pageable) {
        return projectRepo.findAll(pageable)
                .map(projectMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProject(UUID id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        return projectMapper.toResponse(project);
    }

    // ── Update ───────────────────────────────────────────────────────────────

    @Transactional
    public ProjectResponse updateProject(UUID id, ProjectRequest request) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        // No explicit save() needed — the entity is managed within the transaction
        return projectMapper.toResponse(project);
    }

    // ── Delete ───────────────────────────────────────────────────────────────

    @Transactional
    public void deleteProject(UUID id) {
        if (!projectRepo.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        projectRepo.deleteById(id);
    }
}
