package com.wissen.Jira.repository;

import com.wissen.Jira.models.Task;
import com.wissen.Jira.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Task persistence operations.
 *
 * Extends JpaSpecificationExecutor to support dynamic filtering via
 * TaskSpecifications (e.g. filtering by status, project, etc.).
 */
public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {

    /**
     * Returns all tasks with the given status.
     * Spring Data generates the query automatically from the method name.
     * Returns full Task entities — the service layer maps them to TaskResponse DTOs.
     */
    List<Task> findByStatus(TaskStatus status);
}
