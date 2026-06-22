package com.wissen.Jira.specifications;

import com.wissen.Jira.models.Task;
import com.wissen.Jira.models.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA Specifications for building dynamic Task queries.
 *
 * Use these when you need to combine multiple optional filters at runtime,
 * for example: GET /tasks?status=DONE&projectId=...
 *
 * Specifications can be chained with .and() / .or():
 *   taskRepo.findAll(hasStatus(TODO).and(belongsToProject(id)))
 */
public class TaskSpecifications {

    /**
     * Filters tasks by their status field.
     *
     * FIX: Previously had a bug — root.get(status) was passing the enum value
     * as the field name. Correct form is root.get("status").
     */
    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
}
