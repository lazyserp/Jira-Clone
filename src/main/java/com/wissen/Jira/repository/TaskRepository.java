package com.wissen.Jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wissen.Jira.models.Task;
import java.util.*;


public interface TaskRepository extends JpaRepository<Task,UUID>
{
    
}
