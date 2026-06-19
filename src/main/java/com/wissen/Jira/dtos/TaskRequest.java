package com.wissen.Jira.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Task title cannot be empty!")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters!")
    private String title;

    @NotBlank(message = "Status cannot be empty!")
    private String status;
}
