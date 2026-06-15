package com.wissen.Jira.repository;

import com.wissen.Jira.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long>
{

}