package com.wissen.Jira.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;




@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message= " Task title cannot be empty !")
    @Size(min=3 , max=100 , message = "title not more than 100 chars")
    private String title;

    @NotEmpty(message="Status cannot be empty !")
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Project project;
    
}
