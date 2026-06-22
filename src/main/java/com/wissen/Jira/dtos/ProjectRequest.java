package com.wissen.Jira.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Inbound DTO for creating or updating a Project.
 * Validation lives here — NOT on the entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Project name is required!")
    @Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters!")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters!")
    private String description;
}
