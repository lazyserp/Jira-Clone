package com.wissen.Jira.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Outbound DTO for a Project.
 * Includes the project's tasks as a nested list of TaskResponse objects.
 * The entity itself is never serialized directly.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private List<TaskResponse> tasks;
}
