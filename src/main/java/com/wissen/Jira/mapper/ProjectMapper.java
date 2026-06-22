package com.wissen.Jira.mapper;

import com.wissen.Jira.dtos.ProjectRequest;
import com.wissen.Jira.dtos.ProjectResponse;
import com.wissen.Jira.dtos.TaskResponse;
import com.wissen.Jira.models.Project;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProjectMapper centralises all conversions between Project entities and DTOs.
 *
 * Workflow:
 *   ProjectRequest  ──toEntity()──▶  Project (entity)  ──▶  saved to DB
 *   Project (entity) ──toResponse()──▶  ProjectResponse  ──▶  returned to client
 *
 * Keeping mapping logic here (not in the service or controller) means:
 *  - Services stay focused on business rules only
 *  - Mapping is easy to find, test, and change in one place
 */
@Component
public class ProjectMapper {

    private final TaskMapper taskMapper;

    public ProjectMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * Converts a ProjectRequest DTO into a new Project entity.
     * The task list is NOT set here — tasks are added later via Project#addTask().
     */
    public Project toEntity(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    /**
     * Converts a saved Project entity into a ProjectResponse DTO.
     * Includes the project's tasks as nested TaskResponse objects.
     */
    public ProjectResponse toResponse(Project project) {
        List<TaskResponse> taskResponses = project.getTasks()
                .stream()
                .map(taskMapper::toResponse)
                .toList();

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                taskResponses
        );
    }
}
