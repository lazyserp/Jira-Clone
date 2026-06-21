package com.wissen.Jira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissen.Jira.dtos.ProjectRequest;
import com.wissen.Jira.dtos.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetProjectAndTasksFlow() throws Exception {
        // 1. Create a project
        ProjectRequest projectRequest = new ProjectRequest("Jira Clone Project", "A project to manage team tasks.");
        
        String projectResponseString = mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Jira Clone Project")))
                .andExpect(jsonPath("$.description", is("A project to manage team tasks.")))
                .andExpect(jsonPath("$.tasks", hasSize(0)))
                .andReturn().getResponse().getContentAsString();

        // Extract project ID
        String projectId = objectMapper.readTree(projectResponseString).get("id").asText();

        // 2. Create a task for that project
        TaskRequest taskRequest = new TaskRequest("Implement REST DTOs", "In Progress");

        mockMvc.perform(post("/tasks/projects/" + projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Implement REST DTOs")))
                .andExpect(jsonPath("$.status", is("In Progress")))
                .andExpect(jsonPath("$.projectId", is(projectId)));

        // 3. Get Project details and verify tasks are listed inside
        mockMvc.perform(get("/projects/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(projectId)))
                .andExpect(jsonPath("$.name", is("Jira Clone Project")))
                .andExpect(jsonPath("$.tasks", hasSize(1)))
                .andExpect(jsonPath("$.tasks[0].title", is("Implement REST DTOs")))
                .andExpect(jsonPath("$.tasks[0].status", is("In Progress")));

        // 4. Get all projects
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));

        // 5. Delete project
        mockMvc.perform(delete("/projects/" + projectId))
                .andExpect(status().isNoContent());

        // 6. Verify project is deleted (should return 404)
        mockMvc.perform(get("/projects/" + projectId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProjectValidationFails() throws Exception {
        // Name too short (must be >= 3)
        ProjectRequest invalidProject = new ProjectRequest("Ji", "Short name");

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProject)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", containsString("Project name must be between 3 and 100 characters")));
    }
}
