package com.wissen.Jira.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectRequest 
{
    private Long id;
    private String name;
    private String description;


    
}
