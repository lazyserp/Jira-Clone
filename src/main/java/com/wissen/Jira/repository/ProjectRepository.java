package com.wissen.Jira.repository;

import com.wissen.Jira.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
public interface ProjectRepository extends JpaRepository<Project, UUID>
{
    // JPQL example
    @Query("SELECT DISTINCT p from Project p JOIN FETCH p.tasks")
    List<Project> getAllProjectsAndTasks();

}